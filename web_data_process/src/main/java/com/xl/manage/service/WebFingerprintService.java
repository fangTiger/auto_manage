package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.ArticleBean;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WebCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.PublicClass;
import com.xl.tool.StringUtil;
import org.junit.Test;

/**
 * 语义指纹处理
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class WebFingerprintService {

	/**
	 * 语义指纹处理
	 * @return com.xl.manage.bean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 10:17 2017/11/3
	 * @param bean
	 */
	public ArticleBean dealFingerprint(ArticleBean bean){

		JSONObject obj;
		JSONObject jsonObject;
		String result;
		long fingerprint = 0;
		try{

			if("".equals(StringUtil.toTrim(bean.getTitle()))){
				bean.getSigs().setIndexSig(0l);
			}else{
				obj = new JSONObject();
				obj.put("title",bean.getTitle());
				obj.put("type","0");//走默认类型

				result = HttpClientPoolUtil.execute(WebCommonData.INDEX_SIG_URL,obj.toString());
				jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
					fingerprint = Long.parseLong(StringUtil.toTrim(jsonObject.getString("datas")));
					bean.getSigs().setIndexSig(fingerprint);
				}else{//访问接口失败
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"访问indexSig语指纹接口", obj.toJSONString(),"访问indexSig语指纹接口出现异常！",new Exception(WebCommonData.INDEX_SIG_URL+" 访问异常！"));
					return null;
				}
			}

			if("".equals(StringUtil.toTrim(bean.getContentText()))||"".equals(StringUtil.toTrim(PublicClass.StripHTML(bean.getContentText())))){
				bean.getSigs().setIndexSigall(bean.getSigs().getIndexSig());
			}else{
				obj = new JSONObject();
				obj.put("title", bean.getTitle());
				obj.put("content", PublicClass.StripHTML(bean.getContentText()));
				obj.put("limit", 10);
				obj.put("keyword", "");
				obj.put("splitInfo", "");
				obj.put("type","0");//走默认类型
				result = HttpClientPoolUtil.execute(WebCommonData.INDEX_SIG_ALL_URL,obj.toString());
				jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
					fingerprint = Long.parseLong(StringUtil.toTrim(jsonObject.getString("datas")));
					bean.getSigs().setIndexSigall(fingerprint);
				}else{//访问接口失败
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"访问indexSigAll语指纹接口", obj.toJSONString(),"访问indexSigAll语指纹接口出现异常！",new Exception(WebCommonData.INDEX_SIG_ALL_URL+" 访问异常！"));
					return null;
				}
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"语义指纹处理", JSON.toJSONString(bean),"获取语义指纹出现异常！",e);
			bean = null;
		}
		return bean;
	}

	public static void main(String[] args) {
		JSONObject obj;
		String result;
		String datas;
		String dataArray[];
		int emotionValue = 0;

		String content = "<p>  <img src='http://img48.chem17.com/6/20180102/636504830623126324148.jpg' /> </p><p>  各大品牌皆有欧洲的优势渠道，为你采购到最新原装进口的工控产品。</p><p>  另外，我们还能为你采购法国、意大利、西班牙、荷兰等其他欧盟国家的大小品牌，超过2000种；只要您提供品牌的英文拼写和完整的型号；只要该厂家还存在、该型号还生产，我们就能为您采购。</p><p>  选择安徽天欧=选择放心!!!</p><p>  （一流的报价速度+100%原装正品）</p><p>  1.渠道：</p><p>  多家欧美厂家中国区代理；2000家欧洲厂家直接供货</p><p>  产品涉及冶金、电力、石化、机械及科研院所等多个领域</p><p>  2.优势：</p><p>  产品覆盖广：德国有自己的分公司，有超过2000家供应商固定合作</p><p>  采购成本低：源头采购，100%原装并享受德国本国企业的价格折扣</p><p>  国际物流成本低：德国公司集中采购，仓库拼单发货，平均每周200单</p><p>  货期有保障：每周有2次固定航班发往上海，货物全程保险。</p><p>  正规报关，海关A类资质企业</p><p>  不易寻找的欧盟小品牌，我们同样为您采购</p><p>  想询价请点击标题下方 公司展台 展台页面最后左下方有联系方式</p><p>  rexroth R911297810 </p><p>  hawe MV41CR </p><p>  BALLUFF BTL5-E10-M0600-BK-KA02M1HK65</p><p>  IFSYS 74110 </p><p>  MTS RHM0550MD701S1G8100(DGM1-550)</p><p>  PHOENIX US-EMLP (52,5X15) YE CUS</p><p>  Huebner OG 71 DN 256 C1 编码器 </p><p>  NISM 65-250U1B-W84 45.15323 Intermediate Ring 280.250.50 GG-25 </p><p>  Turck IM12-22Ex-R Nr:7541233</p><p>  PHOENIX NBC-MSX/ 5,0-94F/R4RC SCO</p><p>  KUEBLER 8.A02H.1K42.1024</p><p>  Cosmotec TB350002200W00</p><p>  Festo </p><p>  rockwell </p><p>  BINKS JGX-25</p><p>  VIBRO-METER GSV 140 M103 </p><p>  JOKAB WET-L FII</p><p>  SCHUNK RPE200-X1200-Y1200-Z1000381459</p><p>  BARKSDALED2T-M80SS-PAS </p><p>  Lafert GmbH Type ST8052 IEC60034 3~Mot Nr:458508 电机 </p><p>  schmersal 336-11Z-1593-4 IEC60947-5\\\\\\\\IP66 </p><p>  R38.42-40 xiangshu24 </p><p>  NHBB NHBB SSRIF-5632ZZHA5P25LY660P </p><p>  Dold MK9053N.12/111 AC1-10A Tv=0-100s </p><p>  Hawe VP 1 R-3/8-G 24 </p><p>  PHOENIX UC-WMTBA (24X5) SR</p><p>  PHOENIX VS-MSD-MSD-93E/5,0</p><p>  MAHLE 9407 509 32131 AC1 20A 480V Vcc 5-32V </p><p>  STAUBLICT-E1-15/B;33.4022 </p><p>  GHR 76BS1806014V80° BS 1806-014 Viton O-Ring Shore 80° O形圈 </p><p>  Dold ML7853/61 AC220-240V 30S 100MS </p><p>  Staubli RBE11.6813 接头 </p><p>  BALLUFF BES 516-300-S135-S4D </p><p>  knick ZN-194.000-143 </p><p>  Festo </p><p>  Hawe DS2-1 液压阀 </p><p>  BucherDDRB-7M-6-03-S-1 </p><p>  PHOENIX QPD P 4PE2,5 6-11 GY</p><p>  HEMA KABTUEL KLEIN KT17 NR.39900-ICOTEK </p><p>  Bar 边板 199007050 8043554 </p><p>  INTERNORMEN 300368 01.NL 250.25G.30.E.P</p><p>  hydac 0660R020BN4HC 滤芯 </p><p>  HBM K-T40B-200Q-MF-S-M-DU2-0-S</p><p>  GRW SS693-2Z P4C3 </p><p>  ELCIS 编码器 172CR 1021-1828-BZ-B-CM</p><p>  德国海德汉heidenhain光栅尺及其配件 EQN1325 2048 ID:655251-52 </p><p>  heidenhain </p><p>  Hawe EV 22 K 1-24 放大器 </p><p>  BOSCH </p><p>  GEORGIN FP34KX </p><p>  FRAKO LKT8,33-525-EP 电容器 </p><p>  servomex 吸盘 PV023R1D171NFT2 14041050 </p><p>  Oswald Elektro-motoren No.157919-2011; Part No.8113-000008; Cust No.10016768 </p><p>  TOGNELLA FT 257/6-12 </p><p>  duesen-schlick 44392 附件 </p><p>  ATOS DLOH-3A-U 21 24DC 阀门 </p><p>  finder 86.00.0.240.0000 时间模块 </p><p>  SIEMENS 6DD1661-0AD1 模块 </p><p>  PHOENIX DFK-MSTB 2,5/10-G-5,08 WW(1X1)</p><p>  VEM K21R 90 L8 DSD FDS SP.2648 电机 </p><p>  KUKA 00-115-925 </p><p>  LENOIR-ELEC 2560A, 2NC,1NO CEX98-2560-2.1 </p><p>  MTS RHS1950MN021SB1100 传感器</p><p>  MTS RHM0500MD601A02 6针头插座</p><p>  Murrelektronik GmbH 27778 模块 </p><p>  schultz AWEX020D06 </p><p>  PHOENIX 1607130 </p><p>  ELCIS A/X390-1024-12-V-CM5</p><p>  SHC V42254-A6000-G109 插头壳 </p><p>  Staubli MPS630-TS-NH </p><p>  PHOENIX NBC-MS/ 1,0-94B/FS SCO</p><p>  BINKS MX19042UU-SMH</p><p>  DI-SORIC 202601 US 60 K 5000 AI-TSSL</p><p>  PHOENIX HC-BBB 40-EBUC</p><p>  Staubli HPX12.1104/JV </p><p>  MTS GHM0650MR102DA</p><p>  BENDER W35 </p><p>  SMW 17676</p><p>  PHOENIX 1501252 </p><p>  suco 0184 458 01 1 040 压力开关 </p><p>  hydac TFP104-000,904696 温度传感器 </p><p>  Duplomatic TURBO 70 115 84239C 200616375 </p><p>  HIRSCHMANN MM2-4TX1 </p><p>  DELKERWeitereArt.-Nr.:4000862956 </p><p>  KISTLER 8310A2型</p><p>  PHOENIX VS-PPC-J-M</p><p>  Pilz PSWZX1PC0.5V/24-240VACDC2N/0 </p><p>  Mayr EAS-NC GR.0 TYP 454.700.6 S NR.0910638 联轴器 </p><p>  SIEMENS 7ML1201-2EE00</p><p>  WOERNER VPB-B/6/6/0/M3/20/14/20/P</p><p>  Staubli RBE03.7100/1A/45/HPG/JV </p><p>  PULS puls QS10.241 </p><p>  SCHUNKFMS-SK0500301821 </p><p>  rexroth R902487047 </p><p>  NORELEM 02041-306020</p><p>  德国海德汉heidenhain光栅尺及其配件 APK 02 05 ID:607720-N2 </p><p>  Staubli SPI 06 </p><p>  Hornung PEGO-ZD400</p><p>  InterApp DN80 PN16 GGG40 EPDM-KTV </p><p>  PHOENIX TSD-M 3NM</p><p>  MTS RHM01270MR021A01</p><p>  SALTUS 8606001512 5326645B7 </p><p>  IFM PN2221 </p><p>  B&R 0TB704.9. 控制模块 </p><p>  hydac 169-460-252 </p><p>  BERNSTEIN 607.3200.011 安全门开关 </p><p>  BINKS 70157-02</p><p>  NORELEM NLM 6311-29 gerade form</p><p>  BUSH 2021985 </p><p>  brinkmann TC 63/440-560-X+209 泵 </p><p>  Kuka 198265 </p><p>  PHOENIX 2754914 </p><p>  Reiff 32T10/1390 </p><p>  WAYCON MSB-1000-N-F1 </p><p>  42.0001.6336 xiangshu24 </p><p>  Leybold 71218554 </p><p>  PHOENIX SAC-4P- 5,0-PUR/M 8FS BE</p><p>  TANDLER Zahnrad- und Getriebefabrik GmbH & Co. KG 氧传感器 sealing element for seamclosing/TYPE 4641.00000.481;12777 19041660 </p><p>  SMW 47,5 mm 339891</p><p>  pfeiffer BR31a-SRP-60-4.5 </p><p>  SCHMERSAL 弯头 Fab.Nr.3400190746/c Pmax=16bar V=0.94dm3 TS=30 =+80 DE-07-M1002-PTB016 M1 E2 KLASSE 1.0 TM=25 +55 DVGW-REC-NR:PS DG-4703BL0138 734 </p><p>  beck IP2002-B318 </p><p>  Foseco 1634 压力开关 </p><p>  SOFIMA FRF 160 CD 1BB7R2 with Filter </p><p>  BARKSDALE BNA-S22-DN20-500-VA30/02-1GK03-XT </p><p>  hydac EDS 348-5-016-000（16bar) 压力传感器 </p><p>  VAHLE KA10-6N 142074 </p><p>  SIEMENS6SE7038-6GL84-1JA1 </p><p>  kistler 4502A50R </p><p>  TrelleborgSwedishTrelleborgSealingBUSAK+SHAMBAN,seals,GR6500280T47,number:10 </p><p>  德国海德汉heidenhain光栅尺及其配件 ECN413 2048 ID:1065932-11 </p><p>  SCHUNK DPG-plus 100-1 0304331 夹具 </p><p>  Stauff 428 PP DP-ISDOUBLE VON 90-111.441 </p><p>  GHR C31-R1-L-N Nr：00C31R1LN</p><p>  AHP-merkle MBZ 160L.32/20.03.201.150 OMKOM.NR 20038496 164026 </p><p>  PERRIN GmbH1 inch high frequency switching valve seal assembly 731UV31501 16-M valve 600LB </p><p>  REXROTH KSDER1NB/HN0V </p><p>  kuebler </p><p>  PHOENIX TML (104X2,8)R</p><p>  Hawe VZP1-G22-X24</p><p>  WAYCON DK805SALR </p><p>  burgmann 224-60 </p><p>  buehler 62-2-C6F-T-03V001 </p><p>  6DD1661-0AD0 </p><p>  Festo </p><p>  Kraus & Naimer CA10-A214-608-FT2 </p><p>  signal construct MSOG114364;24VDC 20MA </p><p>  heidenhain 385488-02 </p><p>  HEIDENHAINLC183ML440mm+/-3umId-Nr:557680-04 </p><p>  Schunk PZN100-2 </p><p>  BINKS 72771-14</p><p>  MTS RHM0040MP021S1G8100</p><p>  Turck PS250R-301-LI2UPN8X-H1141 Nr:6833309 压力开关 59天 </p><p>  TURCK NI50U-QV40-AP6X2-H1141 1625853 </p><p>  Dold MK9053 DC1-10A UH AC42V 1S </p><p>  TURCK BIM-IKE-AP6X/S70 3M Nr:4621421 </p><p>  KUKA 00-109-802 </p><p>  DIRAK 263-9003 </p><p>  StoerkST181-VRXV.XXF </p><p>  supfina 77750.20/78-000-47C 403085/007 </p><p>  930-491 P44/P45 （for HMD-G-32-0250 Nr; 5100343） O型圈 ABEL Gmbh </p><p>  PV Automotive GmbH MANN+HUMMEL/45/500/92/920 </p><p>  FKB Lumolux KE/424 24VDC 24W G, length 630mm ,NR.591-055-002 荧光灯管 </p><p>SCHUNK 0371129PGN-plus300-1-P</p><p>  KTRROTEXGS24-98ShA-6.0-Φ14-6.0-Φ24KTR.386264 </p><p>  ATOS AGMZO-A-10/210</p><p>  ATOS SP-COU-24DC/80 阀门 </p><p>  AXELENTW322-220120 </p><p>  AirCom RGDJ-08FM 阀 </p><p>  Kuebler 8.3600.0010.1000.5008 </p><p>  SAUER DANFOSS ERR100BLS2620NNN3 S1NPA1NAAANNNNNN Serial No:A133120563 </p><p>  Schmersal 101195504</p><p>  PHOENIX SAC-3P-M 8MS/10,0-600 FB</p><p>  B&R Industrie-Elektronik GmbH 8AC110.60-2 总线模块 </p><p>  HBM T22-200μm </p><p>  HELIOS Ab-nr.20110641.art-nr.16401012. 380V.1225W</p><p>  Hawe R5.1 </p><p>  KUEBLER 8.5822.1821.10240</p><p>  EGE PUR410E, orange</p><p>  DITTEL F21420 </p><p>  BALLUFF BES M08EC-PSC15B-S49G </p><p>  HIMA HIMatrix F3 AIO 8/4 01 </p><p>  BAUER BF60-71/D13MA4W-TF 25668301-1 </p><p>  PARKER </p><p>  elthermELVB-SREx-IT </p><p>  WAYCON SM100-S-KA </p><p>  Camco 80rdm4h20-330 sn00272698 </p><p>  Elesta SGR282Z SB251EL43R336 </p><p>  RHP 7012A5TRSULP4 </p><p>  TURCK NI10-M18-Y1X-H1141 Nr:40153 </p><p>  Bikon Bikon 8000 25x34 </p><p>  Honsberg NJM2-020GM020 </p><p>  DITTELBACH UND KERZLER LHPW-10/4-B </p><p>  SIMRIT 245091;55*80*12 </p><p>  SenoTec CLC 28-L15 液位放大器 </p><p>  Turck NI20-G30K-AD4X Nr.4417220 接近开关 </p><p>  SIEMENS 7ML5221-1BA11</p><p>  FOXBORO IMT25-IEBTB10N-B/9304A-SIZD-NHJ-IN;0.25%,range:0~5m/s,4~20mA,IP65,1.6MPa,24VDC,DN100 </p><p>  MESSKOMT-ST160W/TT/4/6m63519-406 </p><p>  WAYCON LMI12-SL-F-150 </p><p>  Murrelektronik NO:7000-12221-6241000</p><p>  Herion C-TEC2440P TYPE 自动控制器 </p><p>  Hawe RK4 检查阀 </p><p>  BOSCH IM45-2.5A </p><p>  ATOS E-BM-AC-01F 减压阀 </p><p>  SMW 60350010 工具夹具 </p><p>  Festo </p><p>  Pilz PNOZ mc 3p,ID:773732 </p><p>  SCHMIDT-KUPPLUNG GK 35 Φ12 Φ12,NR.45783 联轴器 </p><p>  STEINELST731916-023 </p><p>  SANTERNO DCREG2 </p><p>  ZIMMER GK20N-B</p><p>  Baytek 瘪塘修复器 WS10-500-420A-L10-SAB2 with joint 9043752 </p><p>  SenoTec Ochmann GmbH R3T-5/7L10W，No.13054 液位传感器 </p><p>  HUMMEL AG3.130.0844.13 </p><p>  MTS RHM1045MP151S3B6105</p><p>  Staubli staubli TRF 13.103/IC </p><p>  Parker RP800S-M </p><p>  WAYCON MSB-3750-E-F1 </p><p>  HAHN+KOLB 52788/584 </p><p>  ATOS E-ATR-7/100/ I </p><p>  FAG 22220-E1</p><p>  BINKS 79503-12532</p><p>  STRACK Model of the 12-124 mold：Z5130-18 定位器 </p><p>  ES 51 1OE/1S 控制器 steute </p><p>  Gunda 9904905,NR.VPAC3122SK0212 总线模块 </p><p>  trumpf 1331693 </p><p>  kistler 900 500 405 </p><p>  Balluff </p><p>  optek OPB741WZ </p><p>  JOKAB FOCUS FMC-2</p><p>  EBRO Z011-A DN125/P16 EPDM </p><p>  WAYCON SX120-3125-15,7-G-SR </p><p>  BALLUFF BES 516-300-S166-03 </p><p>  MOOG MODJ761-003 S63JOGB4VPL </p><p>  WOERNER VPA-C.B/A8 </p><p>  Murr 3124248 </p><p>  MTS RHM0365MR051A01</p><p>  CALEFFI Armaturen GmbH 553540 </p><p>  BALLUFF BTL5-E10-M0500-K-NEX-SR32</p><p>  Rexroth R412007185 </p><p>  Siemens MAG6000;IP67 </p><p>  WAYCON SX50-1250-28,8-G-SR-O </p><p>  Festo </p><p>  Pilz 570004 </p><p>  SOHARD Network adapter network equipment and accessories, model specifications : SH-HUB 8/2 </p><p>  WAYCON LRW-M-400-P </p><p>  KTRRotexGS426.0 </p><p>  Absolent 6500004 </p><p>  Turck BS4151-0/13.5 Nr:6904716 插头 </p><p>  TESCOM44-2262-R92-875 </p><p>  RMA06.2201/JV/OD </p><p>";
		try{
			content = PublicClass.StripHTML(content);
			System.out.println(content);
			obj = new JSONObject();
			obj.put("title","After the Show Show: Delmonico's");//В понедельник Совбез ООН проголосует проект резолюции по статусу Иерусалима
//			obj.put("title","各大品牌皆有欧洲的优势渠道");
//			obj.put("content",PublicClass.StripHTML(content));
//			obj.put("limit", 10);
//			obj.put("keyword", "");
//			obj.put("splitInfo", "");
			obj.put("type","0");//走默认类型
			int num = 0;
			while (num<1){
				result =  HttpClientPoolUtil.execute(WebCommonData.INDEX_SIG_URL,obj.toString());
				System.out.println(result);
//			3590595098927606260 1877054356458025654 916244129519130924 4286566814263024136 7838742445256350890
//			3590595098927606300 1877054356458025700 916244129519130900 4286566814263024000 7838742445256351000
				JSONObject jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
//				Long.p
					System.out.println("num:["+num+"]"+Long.parseLong(jsonObject.getString("datas")));
//				datas = jsonObject.getString("datas");
//				datas = datas.replace("<p>","");
//				dataArray = datas.split("</p>");
//
//				for(String data:dataArray){
//					if(data.indexOf("polarity")>-1){
//						emotionValue = (int) Double.parseDouble(StringUtil.toTrim(data.split("：")[1]));
//					}
//				}

				}else{//访问接口失败
					System.out.println("访问接口失败!");
				}
				num++;
			}
		}catch (Exception e){

		}
	}

	@Test
	public void testSig(){
		String title = "平时不留心，刚买的新车也会自燃！";
		String content = "<p> <p data-role=\"original-title\" style=\"display:none\">原标题：平时不留心，刚买的新车也会自燃！</p></p><p> <p> <span>炎炎夏日，如果平时不注意保养和检修，车辆很容易发生自燃，各位车主需要多加注意！</span></p> </p><p> <p> <strong>车辆自燃四大诱因</strong></p> </p><p> <p> <img width=\"100%\" src=\"\" /></p> </p><p> <p> <img width=\"auto\" src=\"http://img.mp.sohu.com/upload/20170807/a9a8bb2db2384eb5af27db4f6e01e347.png\" /></p> </p><p> <p> <span><strong>1. 车内电路老化</strong></span></p> </p><p> <p> <span>在车辆自燃事故中，有不少是因为车辆油路、电路老化造成的。由于这些电路绝大多数都在发动机舱内，所有很容易受到车主的忽视，如果电路绝缘胶皮破损、插头虚接，那么在车辆行驶的颠簸中，难免就会出现“搭铁”，就会造成车内电路短路，引发火源。</span></p> </p><p> <p> <span><strong>2<span>. 车内私改电路</span></strong></span></p> </p><p> <p> <span>其实很多新车出现自燃的情况，绝大多数都是因为私改车内线路造成的。一些车主很喜欢给自己爱车增加“配置”，如果改装工人的水平不高，或者是街边小店的人操刀，那么就会埋下车辆自燃的“火种”！</span></p> </p><p> <p> <span><strong>3. 车内有易燃危险品</strong></span></p> </p><p> <p> 不要把香水、打火机、气压罐……等物品放在车内，至少也要远离太阳光的直晒。如果一旦这些危险物品发生意外爆炸，也很可能引燃车辆，后果同样很严重。</p> </p><p> <p> <strong>如果车辆自燃，怎么办？</strong></p> </p><p> <p> <img width=\"100%\" src=\"\" /></p> </p><p> <p> <img width=\"auto\" src=\"http://img.mp.sohu.com/upload/20170807/8ed2431bfc03469e9fb801f1957e5d65_th.png\" /></p> </p><p> <p> <span><strong>1. 不慌乱不恋财，及时报警</strong></span></p> </p><p> <p> <span>如果车辆已经冒烟自燃，那么必须及时靠边停车，取出灭火器，切勿慌张，切勿恋财！同时立即报警求得援助！</span></p> </p><p> <p> <span><strong>2. 发现有征兆，立即靠边停车</strong></span></p> </p><p> <p> <span>很多时候车辆自燃不会直接起明火，而是先有焦糊的气味，或是烟雾。需要驾驶员保持敏感度和警觉性。</span></p> </p><p> <p> <span><strong>3. 看清火势，切勿盲目打开引擎盖</strong></span></p> </p><p> <p> <span>如果发现发动机舱出现大量烟雾，并带有明火，切勿盲目打开引擎盖！因为引擎盖的打开，会让空气流通得更快，增加火势的蔓延。</span></p> </p><p> <p> <strong>如何预防车辆自燃</strong></p> </p><p> <p> <img width=\"100%\" src=\"\" /></p> </p><p> <p> <img width=\"auto\" src=\"http://img.mp.sohu.com/upload/20170807/14dcd17811234881ab7224ebdc73cb16_th.png\" /></p> </p><p> <p> <strong>1. 车辆勤检查</strong></p> </p><p> <p> <span>预防车辆自燃最好的方法就是“勤检查”，一定要养成定期检查车内、发动机舱内情况的好习惯，第一时间发现，第一时间处理！</span></p> </p><p> <p> <strong>2. 避免私改线路</strong></p> </p><p> <p> <span>私改线路的问题由来已久。购买新车的车主，如果需要“增配”，请到广汽丰田第一店，不要私改线路，造成隐患。</span></p> </p><p> <p> <strong>3. 油路漏油应及时更换</strong></p> </p><p> <p> 如果我们发现车内有漏油情况出现，一定要及时查找漏油点，判断是否影响车辆正常工作，或者是否存在严重的安全问题。</p> </p><p> <p> <span>其实无论是缺乏养护，还是私改线路……可以说绝大多数的“车辆自燃”，都是因人为所致，如果不幸遇到车辆自燃，切勿贪恋财物，切勿慌张行事，需要及时报警，尽快远离，等待消防队救援。</span></p> </p><p> <p> <img width=\"auto\" src=\"\" /><a href=\"//www.sohu.com/?strategyid=00001 \" target=\"_blank\" title=\"点击进入搜狐首页\" id=\"backsohucom\" style=\"white-space: nowrap;\"><span class=\"backword\"><i class=\"backsohu\"></i>返回搜狐，查看更多</span></a></p> <p data-role=\"editor-name\">责任编辑：<span></span></p></p><p> </article></p><p> <div class=\"statement\">声明：本文由入驻搜狐号的作者撰写，除搜狐官方账号外，观点仅代表作者本人，不代表搜狐立场。</p>";
		JSONObject obj;
		obj = new JSONObject();
		obj.put("title",title);
		obj.put("content", content);
		String result =  HttpClientPoolUtil.execute(WebCommonData.SIG_URL,obj.toString());
		System.out.println(result);
	}


}
