package com.xl.tool;

import java.util.Hashtable;

public final class HeritrixHelper {

	@SuppressWarnings("rawtypes")
	private static final Hashtable generators = new Hashtable(10);
	@SuppressWarnings("unused")
	private static final long zero = 0L;
	@SuppressWarnings("unused")
	private static final long one = -9223372036854775808L;
	public final long empty;
	public final int degree;
	public long polynomial;
	private long[][] ByteModTable;
	public static final long[][] polynomials = { null,
			{ -4611686018427387904L, -4611686018427387904L },
			{ -2305843009213693952L, -2305843009213693952L },
			{ -3458764513820540928L, -5764607523034234880L },
			{ -576460752303423488L, -576460752303423488L },
			{ -1441151880758558720L, -4899916394579099648L },
			{ -2738188573441261568L, -5332261958806667264L },
			{ -1945555039024054272L, -1945555039024054272L },
			{ -7602076171001397248L, -3134505340649865216L },
			{ -9169328841326329856L, -8628896886041870336L },
			{ -5719571526760529920L, -1612288666598637568L },
			{ -2742692173068632064L, -3913628076184961024L },
			{ -1776670052997660672L, -2407174000829530112L },
			{ -2005227734086713344L, -7718043861406187520L },
			{ -3112550292466434048L, -5147051424131055616L },
			{ -2113595600120315904L, -7045600142044430336L },
			{ -3682677857793867776L, -7529033414544982016L },
			{ -2812286861060341760L, -6211097204841512960L },
			{ -7338580408428134400L, -181868019327172608L },
			{ -4070603152259284992L, -7580349821236543488L },
			{ -4690912428278415360L, -1656541810593366016L },
			{ -8730258663983284224L, -1124153882377715712L },
			{ -7455702586042089472L, -7995369281233616896L },
			{ -58297206016311296L, -5584750510474264576L },
			{ -8198983991290757120L, -6900195776585007104L },
			{ -3368309616348758016L, -777578196575977472L },
			{ -7950798515817414656L, -5274165001345564672L },
			{ -1814110279349305344L, -3073563053254508544L },
			{ -7333090856108294144L, -299958969764413440L },
			{ -5862047463258456064L, -1088406921112715264L },
			{ -2699705949936943104L, -6180435270106611712L },
			{ -5010893113942605824L, -745326200523587584L },
			{ -7247248171544346624L, -7637035222663430144L },
			{ -379617898928275456L, -2128952956840574976L },
			{ -8982278211127738368L, -8328970742175629312L },
			{ -2699136824088985600L, -657743201922187264L },
			{ -5723859659287166976L, -3191693334452109312L },
			{ -5937852116755283968L, -8585462099679903744L },
			{ -4340513297460625408L, -2535761738024878080L },
			{ -7223736098545991680L, -5810321976182439936L },
			{ -8771886216598519808L, -8198507976365965312L },
			{ -1991715210309664768L, -3768584310714531840L },
			{ -9162551332419141632L, -8616797614972600320L },
			{ -719027294788648960L, -6649233720883544064L },
			{ -3559483092657242112L, -7475962079365038080L },
			{ -713657193466691584L, -3981624362359062528L },
			{ -2043424698022887424L, -3853773324916752384L },
			{ -4041900042031005696L, -2750302262364798976L },
			{ -4436928417950760960L, -8365357674703716352L },
			{ -5903553087924551680L, -232537924545363968L },
			{ -8728448178608234496L, -4183444001187078144L },
			{ -2341229233224585216L, -6731777037870903296L },
			{ -1649801635265304576L, -2988673641163274240L },
			{ -7620924682850272256L, -4999586984279010304L },
			{ -6766504171168936448L, -3233736554084598272L },
			{ -4307058212505990400L, -7352830162319492864L },
			{ -6559722688844616064L, -3544821563555732608L },
			{ -5503660899170514624L, -9177641346571454016L },
			{ -187437317177603296L, -1362648669985435232L },
			{ -24562796859796592L, -1298422235250900464L },
			{ -3825548837209328312L, -4007863133653759080L },
			{ -5023278451590057620L, -8135395730479815484L },
			{ -1370071926941500434L, -7957612307809224958L },
			{ -2163743556238479977L, -2346333074831914631L },
			{ -2935493925047926053L, -7378668602973054136L } };

	public static final HeritrixHelper std64 = make(polynomials[64][0], 64);

	public static final HeritrixHelper std32 = make(polynomials[32][0], 32);

	public static final HeritrixHelper std40 = make(polynomials[40][0], 40);

	public static final HeritrixHelper std24 = make(polynomials[24][0], 24);

	@SuppressWarnings("unchecked")
	public static HeritrixHelper make(long polynomial, int degree) {
		Long l = new Long(polynomial);
		HeritrixHelper fpgen = (HeritrixHelper) generators.get(l);
		if (fpgen == null) {
			fpgen = new HeritrixHelper(polynomial, degree);
			generators.put(l, fpgen);
		}
		return fpgen;
	}

	public long reduce(long fp) {
		int N = 8 - this.degree / 8;
		long local = N == 8 ? 0L : fp & -1L << 8 * N;
		long temp = 0L;
		for (int i = 0; i < N; i++) {
			temp ^= this.ByteModTable[(8 + i)][((int) fp & 0xFF)];
			fp >>>= 8;
		}
		return local ^ temp;
	}

	public long extend_byte(long f, int v) {
		f ^= 0xFF & v;
		int i = (int) f;
		long result = f >>> 8;
		result ^= this.ByteModTable[7][(i & 0xFF)];
		return result;
	}

	public long extend_char(long f, int v) {
		f ^= 0xFFFF & v;
		int i = (int) f;
		long result = f >>> 16;
		result ^= this.ByteModTable[6][(i & 0xFF)];
		i >>>= 8;
		result ^= this.ByteModTable[7][(i & 0xFF)];
		return result;
	}

	public long extend_int(long f, int v) {
		f ^= 0xFFFFFFFF & v;
		int i = (int) f;
		long result = f >>> 32;
		result ^= this.ByteModTable[4][(i & 0xFF)];
		i >>>= 8;
		result ^= this.ByteModTable[5][(i & 0xFF)];
		i >>>= 8;
		result ^= this.ByteModTable[6][(i & 0xFF)];
		i >>>= 8;
		result ^= this.ByteModTable[7][(i & 0xFF)];
		return result;
	}

	public long extend_long(long f, long v) {
		f ^= v;
		long result = this.ByteModTable[0][(int) (f & 0xFF)];
		f >>>= 8;
		result ^= this.ByteModTable[1][(int) (f & 0xFF)];
		f >>>= 8;
		result ^= this.ByteModTable[2][(int) (f & 0xFF)];
		f >>>= 8;
		result ^= this.ByteModTable[3][(int) (f & 0xFF)];
		f >>>= 8;
		result ^= this.ByteModTable[4][(int) (f & 0xFF)];
		f >>>= 8;
		result ^= this.ByteModTable[5][(int) (f & 0xFF)];
		f >>>= 8;
		result ^= this.ByteModTable[6][(int) (f & 0xFF)];
		f >>>= 8;
		result ^= this.ByteModTable[7][(int) (f & 0xFF)];
		return result;
	}

	public long fp(byte[] buf, int start, int n) {
		return extend(this.empty, buf, start, n);
	}

	public long fp(char[] buf, int start, int n) {
		return extend(this.empty, buf, start, n);
	}

	public long fp(CharSequence s) {
		return extend(this.empty, s);
	}

	public long fp(int[] buf, int start, int n) {
		return extend(this.empty, buf, start, n);
	}

	public long fp(long[] buf, int start, int n) {
		return extend(this.empty, buf, start, n);
	}

	public long fp8(String s) {
		return extend8(this.empty, s);
	}

	public long fp8(char[] buf, int start, int n) {
		return extend8(this.empty, buf, start, n);
	}

	public long extend(long f, byte v) {
		return reduce(extend_byte(f, v));
	}

	public long extend(long f, char v) {
		return reduce(extend_char(f, v));
	}

	public long extend(long f, int v) {
		return reduce(extend_int(f, v));
	}

	public long extend(long f, long v) {
		return reduce(extend_long(f, v));
	}

	public long extend(long f, byte[] buf, int start, int n) {
		for (int i = 0; i < n; i++) {
			f = extend_byte(f, buf[(start + i)]);
		}
		return reduce(f);
	}

	public long extend(long f, char[] buf, int start, int n) {
		for (int i = 0; i < n; i++) {
			f = extend_char(f, buf[(start + i)]);
		}
		return reduce(f);
	}

	public long extend(long f, CharSequence s) {
		int n = s.length();
		for (int i = 0; i < n; i++) {
			int v = s.charAt(i);
			f = extend_char(f, v);
		}
		return reduce(f);
	}

	public long extend(long f, int[] buf, int start, int n) {
		for (int i = 0; i < n; i++) {
			f = extend_int(f, buf[(start + i)]);
		}
		return reduce(f);
	}

	public long extend(long f, long[] buf, int start, int n) {
		for (int i = 0; i < n; i++) {
			f = extend_long(f, buf[(start + i)]);
		}
		return reduce(f);
	}

	public long extend8(long f, String s) {
		int n = s.length();
		for (int i = 0; i < n; i++) {
			int x = s.charAt(i);
			f = extend_byte(f, x);
		}
		return reduce(f);
	}

	public long extend8(long f, char[] buf, int start, int n) {
		for (int i = 0; i < n; i++) {
			f = extend_byte(f, buf[(start + i)]);
		}
		return reduce(f);
	}

	private HeritrixHelper(long polynomial, int degree) {
		this.degree = degree;
		this.polynomial = polynomial;
		this.ByteModTable = new long[16][256];

		long[] PowerTable = new long[''];

		long x_to_the_i = -9223372036854775808L;
		long x_to_the_degree_minus_one = -9223372036854775808L >>> degree - 1;
		for (int i = 0; i < 128; i++) {
			PowerTable[i] = x_to_the_i;
			boolean overflow = (x_to_the_i & x_to_the_degree_minus_one) != 0L;
			x_to_the_i >>>= 1;
			if (overflow) {
				x_to_the_i ^= polynomial;
			}
		}
		this.empty = PowerTable[64];

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 256; j++) {
				long v = 0L;
				for (int k = 0; k < 8; k++) {
					if ((j & 1 << k) != 0) {
						v ^= PowerTable[(127 - i * 8 - k)];
					}
				}
				this.ByteModTable[i][j] = v;
			}
		}
	}
	
	/**
	 * 根据url转换aid
	 * @Title: getAidByUrl
	 * @data:2016-4-29上午9:36:50
	 * @author:xinlian
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static long getAidByUrl(String url) throws Exception{
		int index = url.indexOf("://");
		if (index > 0) {
			index = url.indexOf('/', index + "://".length());
		}
		CharSequence hostPlusScheme = index == -1 ? url : url.subSequence(0,
				index);
		long tmp = HeritrixHelper.std32.fp(hostPlusScheme);
		return tmp | HeritrixHelper.std40.fp(url) >>> 24;
	}

	public static void main(String[] args) throws Exception {
		String url = "http://gsj.hinews.cn";
		System.out.println(getAidByUrl(url.toLowerCase()));
		/*url = url.toLowerCase();
		int index = url.indexOf("://");
		if (index > 0) {
			index = url.indexOf('/', index + "://".length());
		}

		CharSequence hostPlusScheme = index == -1 ? url : url.subSequence(0,
				index);

		System.out.println(hostPlusScheme);

		long tmp = HeritrixHelper.std32.fp(hostPlusScheme);
		System.out.println(tmp);

		System.out.println(HeritrixHelper.std40.fp(url));

		long a = tmp | HeritrixHelper.std40.fp(url) >>> 24;

		System.out.println(a);*/
		// -1081643543592356049
		// -1081643543592356049

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * StopWords sw= new StopWords("models/stopwords"); CWSTagger seg = new
		 * CWSTagger("models/seg.m"); AbstractExtractor key = new
		 * WordExtract(seg,sw);
		 * 
		 * System.out.println(key.extract(
		 * "甬温线特别重大铁路交通事故车辆经过近24小时的清理工作，26日深夜已经全部移出事故现场，之前埋下的D301次动车车头被挖出运走",
		 * 20, true));
		 */
		// 处理已经分好词的句子
		// sw=null;
		// key = new WordExtract(seg,sw);
		// System.out.println(key.extract("甬温线 特别 重大 铁路交通事故车辆经过近24小时的清理工作，26日深夜已经全部移出事故现场，之前埋下的D301次动车车头被挖出运走",
		// 20));
		// System.out.println(key.extract("赵嘉亿 是 好人 还是 坏人", 5));

		// key = new WordExtract();
		// System.out.println(key.extract("", 5));

	}

}
