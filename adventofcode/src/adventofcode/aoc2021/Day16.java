package adventofcode.aoc2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Day16 {
	public static void main(String[] args) {
		// List<String> test = IOUtil.readFile("2021/day16.test");
		String data = "020D74FCE27E600A78020200DC298F1070401C8EF1F21A4D6394F9F48F4C1C00E3003500C74602F0080B1720298C400B7002540095003DC00F601B98806351003D004F66011148039450025C00B2007024717AFB5FBC11A7E73AF60F660094E5793A4E811C0123CECED79104ECED791380069D2522B96A53A81286B18263F75A300526246F60094A6651429ADB3B0068937BCF31A009ADB4C289C9C66526014CB33CB81CB3649B849911803B2EB1327F3CFC60094B01CBB4B80351E66E26B2DD0530070401C82D182080803D1C627C330004320C43789C40192D002F93566A9AFE5967372B378001F525DDDCF0C010A00D440010E84D10A2D0803D1761045C9EA9D9802FE00ACF1448844E9C30078723101912594FEE9C9A548D57A5B8B04012F6002092845284D3301A8951C8C008973D30046136001B705A79BD400B9ECCFD30E3004E62BD56B004E465D911C8CBB2258B06009D802C00087C628C71C4001088C113E27C6B10064C01E86F042181002131EE26C5D20043E34C798246009E80293F9E530052A4910A7E87240195CC7C6340129A967EF9352CFDF0802059210972C977094281007664E206CD57292201349AA4943554D91C9CCBADB80232C6927DE5E92D7A10463005A4657D4597002BC9AF51A24A54B7B33A73E2CE005CBFB3B4A30052801F69DB4B08F3B6961024AD4B43E6B319AA020020F15E4B46E40282CCDBF8CA56802600084C788CB088401A8911C20ECC436C2401CED0048325CC7A7F8CAA912AC72B7024007F24B1F789C0F9EC8810090D801AB8803D11E34C3B00043E27C6989B2C52A01348E24B53531291C4FF4884C9C2C10401B8C9D2D875A0072E6FB75E92AC205CA0154CE7398FB0053DAC3F43295519C9AE080250E657410600BC9EAD9CA56001BF3CEF07A5194C013E00542462332DA4295680";

		part1("D2FE28");
		part1("38006F45291200");
		part1("EE00D40C823060");

		part1("8A004A801A8002F478");
		part1("620080001611562C8802118E34");
		part1("C0015000016115A2E0802F182340");
		part1("A0016C880162017C3686B18A3D4780");

		part1(data);

		part2("C200B40A82");
		part2("04005AC33890");
		part2("880086C3E88112");
		part2("CE00C43D881120");
		part2("D8005AC2A8F0");
		part2("F600BC2D8F");
		part2("9C005AC2F8F0");

		part2("9C0141080250320F1802104A08");

		// TODO zusätzliche Testfälle für + * min max of 1 element
		part2("020044FBC0");
		part2("060044FBC0");
		part2("0A0044FBC0");
		part2("0E0044FBC0");

		part2(data); // 4355277277801 ist falsch -- zu niedrig

		System.out.println("#####################");
		BitIterator bi = new BitIterator(data);
		while (bi.hasNext()) {
			System.out.print(bi.next());
		}
		System.out.println("\n\n# " + bi.bitsRead);
	}

	private static void part2(String data) {
		Packet p = new Packet(data);

		System.out.println("::: " + p);

		System.out.println("part2: " + crawl(p));
	}

	private static BigInteger crawl(Packet p) {
		if (p.typeId == 4)
			return BigInteger.valueOf(p.literal);

		List<BigInteger> s = p.subpackets.stream().map(p2 -> crawl(p2)).collect(Collectors.toList());

		// s.stream().filter(l -> l < 0).forEach(l -> System.out.println("negative!"));

		if (s.size() == 1)
			return s.get(0);

		switch (p.typeId) {
		case 0:
			return s.parallelStream().reduce((a, b) -> a.add(b)).orElse(BigInteger.ZERO);
		case 1:
			return s.parallelStream().reduce((a, b) -> a.multiply(b)).orElse(BigInteger.ZERO);
		case 2:
			return s.parallelStream().reduce((a, b) -> a.min(b)).orElse(BigInteger.ZERO);
		case 3:
			return s.parallelStream().reduce((a, b) -> a.max(b)).orElse(BigInteger.ZERO);
		case 5:
			return s.get(0).compareTo(s.get(1)) == 1 ? BigInteger.ONE : BigInteger.ZERO;
		case 6:
			return s.get(0).compareTo(s.get(1)) == -1 ? BigInteger.ONE : BigInteger.ZERO;
		case 7:
			return s.get(0).equals(s.get(1)) ? BigInteger.ONE : BigInteger.ZERO;
		}
		return BigInteger.ZERO;
	}

	private static void part1(String data) {
		Packet p = new Packet(data);
		PacketVisitor v = new PacketVisitor() {
			int sum;

			@Override
			public void visit(Packet p1) {
				// TODO Auto-generated method stub
				sum += p1.version;
				for (Packet p2 : p1.subpackets) {
					p2.visit(this);
				}
			}

			@Override
			public void result() {
				System.out.println("part1: " + sum);
			}
		};
		p.visit(v);
		v.result();
	}

	static class BitIterator implements Iterator<Character> {
		List<Character> buffer = new ArrayList<>();
		int index = 0;

		String input;

		int bitsRead;

		BitIterator(String input) {
			this.input = input;
		}

		@Override
		public boolean hasNext() {
			return !buffer.isEmpty() || index < input.length();
		}

		@Override
		public Character next() {
			if (!buffer.isEmpty()) {
				bitsRead++;
				return buffer.remove(0);
			}
			if (index < input.length()) {
				int n = Integer.parseInt("" + input.charAt(index), 16);
				index++;
				String k = Integer.toBinaryString(n);
				while (k.length() < 4)
					k = "0" + k;
				for (int i = 0; i < k.length(); i++)
					buffer.add(k.charAt(i));
				bitsRead++;
				return buffer.remove(0);
			}
			return null;
		}
	}

	static class Packet {
		int version;
		int typeId;
		long literal = -1;
		int operator;

		List<Packet> subpackets = new ArrayList<>();

		Packet(String input) {
			this(new BitIterator(input));
		}

		Packet(BitIterator it) {
			version = Integer.parseInt("" + it.next() + it.next() + it.next(), 2);
			typeId = Integer.parseInt("" + it.next() + it.next() + it.next(), 2);

			if (typeId == 4) {
				parseLiteral(it);
			} else {
				if (it.next() == '0') {
					parse15bitChildren(it);
				} else {
					parse11bitChildren(it);
				}
			}
			System.out.println("? " + it.bitsRead);
		}

		private void parse11bitChildren(BitIterator it) {
			String l1 = "";
			for (int i = 0; i < 11; i++) {
				l1 += it.next();
			}
			int count = Integer.parseInt(l1, 2);
			for (int i = 0; i < count; i++) {
				subpackets.add(new Packet(it));
			}
		}

		private void parse15bitChildren(BitIterator it) {
			String l1 = "";
			for (int i = 0; i < 15; i++) {
				l1 += it.next();
			}
			int end = Integer.parseInt(l1, 2) + it.bitsRead;
			while (it.bitsRead < end) {
				subpackets.add(new Packet(it));
			}
		}

		private void parseLiteral(Iterator<Character> it) {
			boolean moreAhead = true;
			String raw = "";
			while (moreAhead) {
				moreAhead = it.next() == '1';
				raw += "" + it.next() + it.next() + it.next() + it.next();
			}
			literal = Long.parseLong(raw, 2);
		}

		void visit(PacketVisitor v) {
			v.visit(this);
		}

		@Override
		public String toString() {
			String s = "";
			switch (typeId) {
			case 4:
				return " " + literal + " ";
			case 0:
				s = "+(";
				break;
			case 1:
				s = "*(";
				break;
			case 2:
				s = ".(";
				break;
			case 3:
				s = ":(";
				break;
			case 5:
				s = ">(";
				break;
			case 6:
				s = "<(";
				break;
			case 7:
				s = "=(";
				break;
			}
			for (Packet p1 : subpackets) {
				s += p1.toString();
			}
			s += ")";
			return s;
		}

	}

	interface PacketVisitor {
		void visit(Packet p);

		void result();
	}
}
