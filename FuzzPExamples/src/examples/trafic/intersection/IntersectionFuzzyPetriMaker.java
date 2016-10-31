package examples.trafic.intersection;
import core.TableParser;
import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;

public class IntersectionFuzzyPetriMaker {
    public FuzzyPetriNet net;
    /*input places */
    public int iP8, iP7, iP9, iP10, iP11, iP12, iP0, iP2, iP1, iP4, iP3, iP6, iP5 ;
    /*ordinary places */
    int _P1, _P3, _P5, _P6, _P7, _P8, _P9, _P10, _P11, _P12, _P13, _P14, _P15, _P16, _P17, _P18, _P19, _P20, _P21, _P22, _P23, _P24, _P25, _P26, _P27, _P28, _P29, _P30, _P31, _P32, _P33, _P34, _P35, _P36, _P37, _P38, _P39, _P40, _P44, _P45, _P46, _P47, _P48, _P49, _P50, _P51, _P52, P21, _P54, P23, P22, P25, P24, P27, P26, P29, P28, _P63, _P64, _P65, _P66, P30, P32, P31, _P70, _P71, _P72, _P73, _P74, _P75, _P76, _P77, _P78, _P79, _P80, _P81, _P82, _P83, _P84, _P85, _P86, _P87, _P88, _P89, _P90, _P91, _P92, _P93, _P94, _P95, _P96, _P97, _P98, P50, P52, _P101, P51, _P103, P53, _P105, _P106, _P107, _P108, _P109, _P110, _P111, _P112, _P113, _P114, _P115, _P119, _P121, _P123, _P125, _P127 ;
    /* output transitions */
    public int oT6, oT5, oT7, oT0, oT2, oT1, oT4, oT3;
    /* table for out transitions */
    String oT6_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT5_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT7_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT2_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT4_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT3_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    /* ordinary transitions */
    int c_T50, bII_oT7, bII_oT5, bII_oT6, bIV_sp_T21, c_oT0, c_oT1, bIV_sp_T22, c_oT2, c_oT3, bIV_oT6, bIV_oT7, bIII_sp_oT1, bII_sp_oT0, bIII_T2, bIII_sp_oT0, bII_sp_oT1, bIII_T1, bIII_T4, bIII_T3, bIV_oT5, bII_T4, bII_T2, bII_T3, bII_T1, bIV_sp_T11, bIV_sp_T12, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, bIII_sp_T11, bIII_sp_T12, bI_sp_T12, bI_sp_T11, bIII_sp_T1, bIV_T3, bIII_sp_T0, bIV_T4, T10, bIV_T1, bIV_T2, T12, T11, bIV_sp_T1, bIV_sp_T0, c_T0, c_T1, bI_sp_T21, c_T2, c_T3, c_T4, c_T5, bI_oT5, bI_sp_T22, T21, bI_oT7, T20, bI_oT6, T23, T22, c_T11, c_T10, bI_sp_T1, bI_sp_T0, c_T20, c_T22, c_T21, bIV_sp_oT0, bIII_sp_T22, bII_sp_T21, bII_sp_T22, bIII_sp_T21, bIV_sp_oT1, bII_sp_T12, bIII_oT5, bI_T3, bI_T4, bI_T1, bI_T2, bII_sp_T11, bII_sp_T0, bII_sp_T1, bI_sp_oT0, bI_sp_oT1, bIII_oT7, bIII_oT6;
    /* table for ordinary transitions */
    String c_T50_tableStr = "{[<PL,PL><PM,PL><PM,ZR><PM,NL><PL,NL><FF,FF>]}";
    String bII_oT7_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bII_oT5_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bII_oT6_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIV_sp_T21_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><FF>]"+//
    	"[<NL><NL><NM><ZR><PM><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NM><ZR><PM><PL><PL><FF>]"+//
    	"[<ZR><PM><PL><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String c_oT0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String c_oT1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIV_sp_T22_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><FF>]"+//
    	"[<NL><NL><NM><ZR><PM><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NM><ZR><PM><PL><PL><FF>]"+//
    	"[<ZR><PM><PL><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String c_oT2_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String c_oT3_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIV_oT6_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIV_oT7_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIII_sp_oT1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bII_sp_oT0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIII_T2_tableStr = "{"+
    	"[<NL,NL><NL,NL><NL,NL><NL,NL><NL,NL><FF,FF>]"+//
    	"[<NM,NM><NM,NM><NM,NM><NM,NM><NM,NM><FF,FF>]"+//
    	"[<ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<PM,PM><PM,PM><PM,PM><PM,PM><PM,PM><FF,FF>]"+//
    	"[<PL,PL><PL,PL><PL,PL><PL,PL><PL,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bIII_sp_oT0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bII_sp_oT1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIII_T1_tableStr = "{"+
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    "}";
    String bIII_T4_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIII_T3_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><NM,PM><NM,PM><PM,PM><FF,FF>]"+//
    	"[<FF,FF><FF,FF><NL,PL><NM,PL><ZR,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bIV_oT5_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bII_T4_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bII_T2_tableStr = "{"+
    	"[<NL,NL><NL,NL><NL,NL><NL,NL><NL,NL><FF,FF>]"+//
    	"[<NM,NM><NM,NM><NM,NM><NM,NM><NM,NM><FF,FF>]"+//
    	"[<ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<PM,PM><PM,PM><PM,PM><PM,PM><PM,PM><FF,FF>]"+//
    	"[<PL,PL><PL,PL><PL,PL><PL,PL><PL,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bII_T3_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><NM,PM><NM,PM><PM,PM><FF,FF>]"+//
    	"[<FF,FF><FF,FF><NL,PL><NM,PL><ZR,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bII_T1_tableStr = "{"+
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    "}";
    String bIV_sp_T11_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PM,ZR><ZR,ZR><ZR,PM><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PL,ZR><ZR,ZR><ZR,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bIV_sp_T12_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PM,ZR><PM,PM><ZR,PM><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PL,ZR><PL,PL><ZR,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String T0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T2_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T3_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T4_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T5_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T6_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T7_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T8_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T9_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIII_sp_T11_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PM,ZR><ZR,ZR><ZR,PM><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PL,ZR><ZR,ZR><ZR,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bIII_sp_T12_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PM,ZR><PM,PM><ZR,PM><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PL,ZR><PL,PL><ZR,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bI_sp_T12_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PM,ZR><PM,PM><ZR,PM><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PL,ZR><PL,PL><ZR,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bI_sp_T11_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PM,ZR><ZR,ZR><ZR,PM><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PL,ZR><ZR,ZR><ZR,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bIII_sp_T1_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String bIV_T3_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><NM,PM><NM,PM><PM,PM><FF,FF>]"+//
    	"[<FF,FF><FF,FF><NL,PL><NM,PL><ZR,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bIII_sp_T0_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String bIV_T4_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T10_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIV_T1_tableStr = "{"+
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    "}";
    String bIV_T2_tableStr = "{"+
    	"[<NL,NL><NL,NL><NL,NL><NL,NL><NL,NL><FF,FF>]"+//
    	"[<NM,NM><NM,NM><NM,NM><NM,NM><NM,NM><FF,FF>]"+//
    	"[<ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<PM,PM><PM,PM><PM,PM><PM,PM><PM,PM><FF,FF>]"+//
    	"[<PL,PL><PL,PL><PL,PL><PL,PL><PL,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String T12_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T11_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIV_sp_T1_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String bIV_sp_T0_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String c_T0_tableStr = "{"+
    	"[<NL,NL><NL,NL><NL,NL><NL,NL><NL,NL><FF,FF>]"+//
    	"[<NM,NM><NM,NM><NM,NM><NM,NM><NM,NM><FF,FF>]"+//
    	"[<ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<PM,PM><PM,PM><PM,PM><PM,PM><PM,PM><FF,FF>]"+//
    	"[<PL,PL><PL,PL><PL,PL><PL,PL><PL,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String c_T1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bI_sp_T21_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><FF>]"+//
    	"[<NL><NL><NM><ZR><PM><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NM><ZR><PM><PL><PL><FF>]"+//
    	"[<ZR><PM><PL><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String c_T2_tableStr = "{"+
    	"[<PL><PM><ZR><PM><PL><FF>]"+//
    	"[<PL><PM><ZR><PM><PL><FF>]"+//
    	"[<PL><PM><ZR><PM><PL><FF>]"+//
    	"[<PL><PM><ZR><PM><PL><FF>]"+//
    	"[<PL><PM><ZR><PM><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String c_T3_tableStr = "{"+
    	"[<NL,ZR><NL,PM><NL,PL><NL,PL><NL,PL><FF,FF>]"+//
    	"[<NM,NM><NM,ZR><NM,PM><NM,PL><NM,PL><FF,FF>]"+//
    	"[<ZR,NL><ZR,NM><ZR,ZR><ZR,PM><ZR,PL><FF,FF>]"+//
    	"[<PM,NL><PM,NL><PM,NM><PM,ZR><PM,PM><FF,FF>]"+//
    	"[<PL,NL><PL,NL><PL,NL><PL,NM><PL,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String c_T4_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String c_T5_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bI_oT5_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bI_sp_T22_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><FF>]"+//
    	"[<NL><NL><NM><ZR><PM><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NM><ZR><PM><PL><PL><FF>]"+//
    	"[<ZR><PM><PL><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String T21_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><NL>]"+//
    	"[<NL><NL><NM><ZR><PM><NM>]"+//
    	"[<NL><NM><ZR><PM><PL><ZR>]"+//
    	"[<NM><ZR><PM><PL><PL><PM>]"+//
    	"[<ZR><PM><PL><PL><PL><PL>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    "}";
    String bI_oT7_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T20_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><NL>]"+//
    	"[<NL><NL><NM><ZR><PM><NM>]"+//
    	"[<NL><NM><ZR><PM><PL><ZR>]"+//
    	"[<NM><ZR><PM><PL><PL><PM>]"+//
    	"[<ZR><PM><PL><PL><PL><PL>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    "}";
    String bI_oT6_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T23_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><NL>]"+//
    	"[<NL><NL><NM><ZR><PM><NM>]"+//
    	"[<NL><NM><ZR><PM><PL><ZR>]"+//
    	"[<NM><ZR><PM><PL><PL><PM>]"+//
    	"[<ZR><PM><PL><PL><PL><PL>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    "}";
    String T22_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><NL>]"+//
    	"[<NL><NL><NM><ZR><PM><NM>]"+//
    	"[<NL><NM><ZR><PM><PL><ZR>]"+//
    	"[<NM><ZR><PM><PL><PL><PM>]"+//
    	"[<ZR><PM><PL><PL><PL><PL>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    "}";
    String c_T11_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><NL,NL>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><NL,NL>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><ZR,ZR>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><PL,PL>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><PL,PL>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String c_T10_tableStr = "{"+
    	"[<NL><NL><NL><NL><NL><NL>]"+//
    	"[<NL><NL><NL><NL><NL><NL>]"+//
    	"[<NL><NL><NL><NL><NL><NL>]"+//
    	"[<PL><PL><PL><PL><PL><PL>]"+//
    	"[<PL><PL><PL><PL><PL><PL>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String bI_sp_T1_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String bI_sp_T0_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String c_T20_tableStr = "{"+
    	"[<PL,FF><PL,FF><FF,FF><FF,PL><FF,PL><FF,FF>]"+//
    	"[<PL,FF><PL,FF><FF,FF><FF,PL><FF,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,PL><FF,PL><FF,FF><PL,FF><PL,FF><FF,FF>]"+//
    	"[<FF,PL><FF,PL><FF,FF><PL,FF><PL,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String c_T22_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String c_T21_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String bIV_sp_oT0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIII_sp_T22_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><FF>]"+//
    	"[<NL><NL><NM><ZR><PM><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NM><ZR><PM><PL><PL><FF>]"+//
    	"[<ZR><PM><PL><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String bII_sp_T21_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><FF>]"+//
    	"[<NL><NL><NM><ZR><PM><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NM><ZR><PM><PL><PL><FF>]"+//
    	"[<ZR><PM><PL><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String bII_sp_T22_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><FF>]"+//
    	"[<NL><NL><NM><ZR><PM><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NM><ZR><PM><PL><PL><FF>]"+//
    	"[<ZR><PM><PL><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String bIII_sp_T21_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><FF>]"+//
    	"[<NL><NL><NM><ZR><PM><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NM><ZR><PM><PL><PL><FF>]"+//
    	"[<ZR><PM><PL><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String bIV_sp_oT1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bII_sp_T12_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PM,ZR><PM,PM><ZR,PM><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PL,ZR><PL,PL><ZR,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bIII_oT5_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bI_T3_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><NM,PM><NM,PM><PM,PM><FF,FF>]"+//
    	"[<FF,FF><FF,FF><NL,PL><NM,PL><ZR,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bI_T4_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bI_T1_tableStr = "{"+
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    "}";
    String bI_T2_tableStr = "{"+
    	"[<NL,NL><NL,NL><NL,NL><NL,NL><NL,NL><FF,FF>]"+//
    	"[<NM,NM><NM,NM><NM,NM><NM,NM><NM,NM><FF,FF>]"+//
    	"[<ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<PM,PM><PM,PM><PM,PM><PM,PM><PM,PM><FF,FF>]"+//
    	"[<PL,PL><PL,PL><PL,PL><PL,PL><PL,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bII_sp_T11_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PM,ZR><ZR,ZR><ZR,PM><FF,FF>]"+//
    	"[<FF,FF><FF,FF><PL,ZR><ZR,ZR><ZR,PL><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String bII_sp_T0_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String bII_sp_T1_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String bI_sp_oT0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bI_sp_oT1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIII_oT7_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String bIII_oT6_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";

    public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();

    private static TableParser parser = new TableParser(true);

    public IntersectionFuzzyPetriMaker (){
        net = new FuzzyPetriNet();
        addPlaces();
        addTransitions();
        addArcs();
        putInitialMarking();

    }

    private void addPlaces(){
        iP8 = net.addInputPlace();
        iP7 = net.addInputPlace();
        iP9 = net.addInputPlace();
        iP10 = net.addInputPlace();
        iP11 = net.addInputPlace();
        iP12 = net.addInputPlace();
        iP0 = net.addInputPlace();
        iP2 = net.addInputPlace();
        iP1 = net.addInputPlace();
        iP4 = net.addInputPlace();
        iP3 = net.addInputPlace();
        iP6 = net.addInputPlace();
        iP5 = net.addInputPlace();
        _P1 = net.addPlace();
        _P3 = net.addPlace();
        _P5 = net.addPlace();
        _P6 = net.addPlace();
        _P7 = net.addPlace();
        _P8 = net.addPlace();
        _P9 = net.addPlace();
        _P10 = net.addPlace();
        _P11 = net.addPlace();
        _P12 = net.addPlace();
        _P13 = net.addPlace();
        _P14 = net.addPlace();
        _P15 = net.addPlace();
        _P16 = net.addPlace();
        _P17 = net.addPlace();
        _P18 = net.addPlace();
        _P19 = net.addPlace();
        _P20 = net.addPlace();
        _P21 = net.addPlace();
        _P22 = net.addPlace();
        _P23 = net.addPlace();
        _P24 = net.addPlace();
        _P25 = net.addPlace();
        _P26 = net.addPlace();
        _P27 = net.addPlace();
        _P28 = net.addPlace();
        _P29 = net.addPlace();
        _P30 = net.addPlace();
        _P31 = net.addPlace();
        _P32 = net.addPlace();
        _P33 = net.addPlace();
        _P34 = net.addPlace();
        _P35 = net.addPlace();
        _P36 = net.addPlace();
        _P37 = net.addPlace();
        _P38 = net.addPlace();
        _P39 = net.addPlace();
        _P40 = net.addPlace();
        _P44 = net.addPlace();
        _P45 = net.addPlace();
        _P46 = net.addPlace();
        _P47 = net.addPlace();
        _P48 = net.addPlace();
        _P49 = net.addPlace();
        _P50 = net.addPlace();
        _P51 = net.addPlace();
        _P52 = net.addPlace();
        P21 = net.addPlace();
        _P54 = net.addPlace();
        P23 = net.addPlace();
        P22 = net.addPlace();
        P25 = net.addPlace();
        P24 = net.addPlace();
        P27 = net.addPlace();
        P26 = net.addPlace();
        P29 = net.addPlace();
        P28 = net.addPlace();
        _P63 = net.addPlace();
        _P64 = net.addPlace();
        _P65 = net.addPlace();
        _P66 = net.addPlace();
        P30 = net.addPlace();
        P32 = net.addPlace();
        P31 = net.addPlace();
        _P70 = net.addPlace();
        _P71 = net.addPlace();
        _P72 = net.addPlace();
        _P73 = net.addPlace();
        _P74 = net.addPlace();
        _P75 = net.addPlace();
        _P76 = net.addPlace();
        _P77 = net.addPlace();
        _P78 = net.addPlace();
        _P79 = net.addPlace();
        _P80 = net.addPlace();
        _P81 = net.addPlace();
        _P82 = net.addPlace();
        _P83 = net.addPlace();
        _P84 = net.addPlace();
        _P85 = net.addPlace();
        _P86 = net.addPlace();
        _P87 = net.addPlace();
        _P88 = net.addPlace();
        _P89 = net.addPlace();
        _P90 = net.addPlace();
        _P91 = net.addPlace();
        _P92 = net.addPlace();
        _P93 = net.addPlace();
        _P94 = net.addPlace();
        _P95 = net.addPlace();
        _P96 = net.addPlace();
        _P97 = net.addPlace();
        _P98 = net.addPlace();
        P50 = net.addPlace();
        P52 = net.addPlace();
        _P101 = net.addPlace();
        P51 = net.addPlace();
        _P103 = net.addPlace();
        P53 = net.addPlace();
        _P105 = net.addPlace();
        _P106 = net.addPlace();
        _P107 = net.addPlace();
        _P108 = net.addPlace();
        _P109 = net.addPlace();
        _P110 = net.addPlace();
        _P111 = net.addPlace();
        _P112 = net.addPlace();
        _P113 = net.addPlace();
        _P114 = net.addPlace();
        _P115 = net.addPlace();
        _P119 = net.addPlace();
        _P121 = net.addPlace();
        _P123 = net.addPlace();
        _P125 = net.addPlace();
        _P127 = net.addPlace();
        nameStore.addPlaceName(iP8, "iP8" );
        nameStore.addPlaceName(iP7, "iP7" );
        nameStore.addPlaceName(iP9, "iP9" );
        nameStore.addPlaceName(iP10, "iP10" );
        nameStore.addPlaceName(iP11, "iP11" );
        nameStore.addPlaceName(iP12, "iP12" );
        nameStore.addPlaceName(iP0, "iP0" );
        nameStore.addPlaceName(iP2, "iP2" );
        nameStore.addPlaceName(iP1, "iP1" );
        nameStore.addPlaceName(iP4, "iP4" );
        nameStore.addPlaceName(iP3, "iP3" );
        nameStore.addPlaceName(iP6, "iP6" );
        nameStore.addPlaceName(iP5, "iP5" );
        nameStore.addPlaceName(_P1, "_P1" );
        nameStore.addPlaceName(_P3, "_P3" );
        nameStore.addPlaceName(_P5, "_P5" );
        nameStore.addPlaceName(_P6, "_P6" );
        nameStore.addPlaceName(_P7, "_P7" );
        nameStore.addPlaceName(_P8, "_P8" );
        nameStore.addPlaceName(_P9, "_P9" );
        nameStore.addPlaceName(_P10, "_P10" );
        nameStore.addPlaceName(_P11, "_P11" );
        nameStore.addPlaceName(_P12, "_P12" );
        nameStore.addPlaceName(_P13, "_P13" );
        nameStore.addPlaceName(_P14, "_P14" );
        nameStore.addPlaceName(_P15, "_P15" );
        nameStore.addPlaceName(_P16, "_P16" );
        nameStore.addPlaceName(_P17, "_P17" );
        nameStore.addPlaceName(_P18, "_P18" );
        nameStore.addPlaceName(_P19, "_P19" );
        nameStore.addPlaceName(_P20, "_P20" );
        nameStore.addPlaceName(_P21, "_P21" );
        nameStore.addPlaceName(_P22, "_P22" );
        nameStore.addPlaceName(_P23, "_P23" );
        nameStore.addPlaceName(_P24, "_P24" );
        nameStore.addPlaceName(_P25, "_P25" );
        nameStore.addPlaceName(_P26, "_P26" );
        nameStore.addPlaceName(_P27, "_P27" );
        nameStore.addPlaceName(_P28, "_P28" );
        nameStore.addPlaceName(_P29, "_P29" );
        nameStore.addPlaceName(_P30, "_P30" );
        nameStore.addPlaceName(_P31, "_P31" );
        nameStore.addPlaceName(_P32, "_P32" );
        nameStore.addPlaceName(_P33, "_P33" );
        nameStore.addPlaceName(_P34, "_P34" );
        nameStore.addPlaceName(_P35, "_P35" );
        nameStore.addPlaceName(_P36, "_P36" );
        nameStore.addPlaceName(_P37, "_P37" );
        nameStore.addPlaceName(_P38, "_P38" );
        nameStore.addPlaceName(_P39, "_P39" );
        nameStore.addPlaceName(_P40, "_P40" );
        nameStore.addPlaceName(_P44, "_P44" );
        nameStore.addPlaceName(_P45, "_P45" );
        nameStore.addPlaceName(_P46, "_P46" );
        nameStore.addPlaceName(_P47, "_P47" );
        nameStore.addPlaceName(_P48, "_P48" );
        nameStore.addPlaceName(_P49, "_P49" );
        nameStore.addPlaceName(_P50, "_P50" );
        nameStore.addPlaceName(_P51, "_P51" );
        nameStore.addPlaceName(_P52, "_P52" );
        nameStore.addPlaceName(P21, "P21" );
        nameStore.addPlaceName(_P54, "_P54" );
        nameStore.addPlaceName(P23, "P23" );
        nameStore.addPlaceName(P22, "P22" );
        nameStore.addPlaceName(P25, "P25" );
        nameStore.addPlaceName(P24, "P24" );
        nameStore.addPlaceName(P27, "P27" );
        nameStore.addPlaceName(P26, "P26" );
        nameStore.addPlaceName(P29, "P29" );
        nameStore.addPlaceName(P28, "P28" );
        nameStore.addPlaceName(_P63, "_P63" );
        nameStore.addPlaceName(_P64, "_P64" );
        nameStore.addPlaceName(_P65, "_P65" );
        nameStore.addPlaceName(_P66, "_P66" );
        nameStore.addPlaceName(P30, "P30" );
        nameStore.addPlaceName(P32, "P32" );
        nameStore.addPlaceName(P31, "P31" );
        nameStore.addPlaceName(_P70, "_P70" );
        nameStore.addPlaceName(_P71, "_P71" );
        nameStore.addPlaceName(_P72, "_P72" );
        nameStore.addPlaceName(_P73, "_P73" );
        nameStore.addPlaceName(_P74, "_P74" );
        nameStore.addPlaceName(_P75, "_P75" );
        nameStore.addPlaceName(_P76, "_P76" );
        nameStore.addPlaceName(_P77, "_P77" );
        nameStore.addPlaceName(_P78, "_P78" );
        nameStore.addPlaceName(_P79, "_P79" );
        nameStore.addPlaceName(_P80, "_P80" );
        nameStore.addPlaceName(_P81, "_P81" );
        nameStore.addPlaceName(_P82, "_P82" );
        nameStore.addPlaceName(_P83, "_P83" );
        nameStore.addPlaceName(_P84, "_P84" );
        nameStore.addPlaceName(_P85, "_P85" );
        nameStore.addPlaceName(_P86, "_P86" );
        nameStore.addPlaceName(_P87, "_P87" );
        nameStore.addPlaceName(_P88, "_P88" );
        nameStore.addPlaceName(_P89, "_P89" );
        nameStore.addPlaceName(_P90, "_P90" );
        nameStore.addPlaceName(_P91, "_P91" );
        nameStore.addPlaceName(_P92, "_P92" );
        nameStore.addPlaceName(_P93, "_P93" );
        nameStore.addPlaceName(_P94, "_P94" );
        nameStore.addPlaceName(_P95, "_P95" );
        nameStore.addPlaceName(_P96, "_P96" );
        nameStore.addPlaceName(_P97, "_P97" );
        nameStore.addPlaceName(_P98, "_P98" );
        nameStore.addPlaceName(P50, "P50" );
        nameStore.addPlaceName(P52, "P52" );
        nameStore.addPlaceName(_P101, "_P101" );
        nameStore.addPlaceName(P51, "P51" );
        nameStore.addPlaceName(_P103, "_P103" );
        nameStore.addPlaceName(P53, "P53" );
        nameStore.addPlaceName(_P105, "_P105" );
        nameStore.addPlaceName(_P106, "_P106" );
        nameStore.addPlaceName(_P107, "_P107" );
        nameStore.addPlaceName(_P108, "_P108" );
        nameStore.addPlaceName(_P109, "_P109" );
        nameStore.addPlaceName(_P110, "_P110" );
        nameStore.addPlaceName(_P111, "_P111" );
        nameStore.addPlaceName(_P112, "_P112" );
        nameStore.addPlaceName(_P113, "_P113" );
        nameStore.addPlaceName(_P114, "_P114" );
        nameStore.addPlaceName(_P115, "_P115" );
        nameStore.addPlaceName(_P119, "_P119" );
        nameStore.addPlaceName(_P121, "_P121" );
        nameStore.addPlaceName(_P123, "_P123" );
        nameStore.addPlaceName(_P125, "_P125" );
        nameStore.addPlaceName(_P127, "_P127" );
    }

    private void addTransitions(){
        oT6 =  net.addOuputTransition(parser.parseOneXOneTable(oT6_tableStr));
        oT5 =  net.addOuputTransition(parser.parseOneXOneTable(oT5_tableStr));
        oT7 =  net.addOuputTransition(parser.parseOneXOneTable(oT7_tableStr));
        oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
        oT2 =  net.addOuputTransition(parser.parseOneXOneTable(oT2_tableStr));
        oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
        oT4 =  net.addOuputTransition(parser.parseOneXOneTable(oT4_tableStr));
        oT3 =  net.addOuputTransition(parser.parseOneXOneTable(oT3_tableStr));
        nameStore.addTransitionName(oT6, "oT6" );
        nameStore.addTransitionName(oT5, "oT5" );
        nameStore.addTransitionName(oT7, "oT7" );
        nameStore.addTransitionName(oT0, "oT0" );
        nameStore.addTransitionName(oT2, "oT2" );
        nameStore.addTransitionName(oT1, "oT1" );
        nameStore.addTransitionName(oT4, "oT4" );
        nameStore.addTransitionName(oT3, "oT3" );
        c_T50 =  net.addTransition(0,parser.parseTable(c_T50_tableStr));
        bII_oT7 =  net.addTransition(0,parser.parseTable(bII_oT7_tableStr));
        bII_oT5 =  net.addTransition(0,parser.parseTable(bII_oT5_tableStr));
        bII_oT6 =  net.addTransition(0,parser.parseTable(bII_oT6_tableStr));
        bIV_sp_T21 =  net.addTransition(0,parser.parseTable(bIV_sp_T21_tableStr));
        c_oT0 =  net.addTransition(0,parser.parseTable(c_oT0_tableStr));
        c_oT1 =  net.addTransition(0,parser.parseTable(c_oT1_tableStr));
        bIV_sp_T22 =  net.addTransition(0,parser.parseTable(bIV_sp_T22_tableStr));
        c_oT2 =  net.addTransition(0,parser.parseTable(c_oT2_tableStr));
        c_oT3 =  net.addTransition(0,parser.parseTable(c_oT3_tableStr));
        bIV_oT6 =  net.addTransition(0,parser.parseTable(bIV_oT6_tableStr));
        bIV_oT7 =  net.addTransition(0,parser.parseTable(bIV_oT7_tableStr));
        bIII_sp_oT1 =  net.addTransition(0,parser.parseTable(bIII_sp_oT1_tableStr));
        bII_sp_oT0 =  net.addTransition(0,parser.parseTable(bII_sp_oT0_tableStr));
        bIII_T2 =  net.addTransition(0,parser.parseTable(bIII_T2_tableStr));
        bIII_sp_oT0 =  net.addTransition(0,parser.parseTable(bIII_sp_oT0_tableStr));
        bII_sp_oT1 =  net.addTransition(0,parser.parseTable(bII_sp_oT1_tableStr));
        bIII_T1 =  net.addTransition(0,parser.parseTable(bIII_T1_tableStr));
        bIII_T4 =  net.addTransition(0,parser.parseTable(bIII_T4_tableStr));
        bIII_T3 =  net.addTransition(0,parser.parseTable(bIII_T3_tableStr));
        bIV_oT5 =  net.addTransition(0,parser.parseTable(bIV_oT5_tableStr));
        bII_T4 =  net.addTransition(0,parser.parseTable(bII_T4_tableStr));
        bII_T2 =  net.addTransition(0,parser.parseTable(bII_T2_tableStr));
        bII_T3 =  net.addTransition(0,parser.parseTable(bII_T3_tableStr));
        bII_T1 =  net.addTransition(0,parser.parseTable(bII_T1_tableStr));
        bIV_sp_T11 =  net.addTransition(0,parser.parseTable(bIV_sp_T11_tableStr));
        bIV_sp_T12 =  net.addTransition(0,parser.parseTable(bIV_sp_T12_tableStr));
        T0 =  net.addTransition(0,parser.parseTable(T0_tableStr));
        T1 =  net.addTransition(0,parser.parseTable(T1_tableStr));
        T2 =  net.addTransition(0,parser.parseTable(T2_tableStr));
        T3 =  net.addTransition(0,parser.parseTable(T3_tableStr));
        T4 =  net.addTransition(0,parser.parseTable(T4_tableStr));
        T5 =  net.addTransition(0,parser.parseTable(T5_tableStr));
        T6 =  net.addTransition(0,parser.parseTable(T6_tableStr));
        T7 =  net.addTransition(0,parser.parseTable(T7_tableStr));
        T8 =  net.addTransition(0,parser.parseTable(T8_tableStr));
        T9 =  net.addTransition(0,parser.parseTable(T9_tableStr));
        bIII_sp_T11 =  net.addTransition(0,parser.parseTable(bIII_sp_T11_tableStr));
        bIII_sp_T12 =  net.addTransition(0,parser.parseTable(bIII_sp_T12_tableStr));
        bI_sp_T12 =  net.addTransition(0,parser.parseTable(bI_sp_T12_tableStr));
        bI_sp_T11 =  net.addTransition(0,parser.parseTable(bI_sp_T11_tableStr));
        bIII_sp_T1 =  net.addTransition(0,parser.parseTable(bIII_sp_T1_tableStr));
        bIV_T3 =  net.addTransition(0,parser.parseTable(bIV_T3_tableStr));
        bIII_sp_T0 =  net.addTransition(0,parser.parseTable(bIII_sp_T0_tableStr));
        bIV_T4 =  net.addTransition(0,parser.parseTable(bIV_T4_tableStr));
        T10 =  net.addTransition(0,parser.parseTable(T10_tableStr));
        bIV_T1 =  net.addTransition(0,parser.parseTable(bIV_T1_tableStr));
        bIV_T2 =  net.addTransition(0,parser.parseTable(bIV_T2_tableStr));
        T12 =  net.addTransition(0,parser.parseTable(T12_tableStr));
        T11 =  net.addTransition(0,parser.parseTable(T11_tableStr));
        bIV_sp_T1 =  net.addTransition(0,parser.parseTable(bIV_sp_T1_tableStr));
        bIV_sp_T0 =  net.addTransition(0,parser.parseTable(bIV_sp_T0_tableStr));
        c_T0 =  net.addTransition(0,parser.parseTable(c_T0_tableStr));
        c_T1 =  net.addTransition(12,parser.parseTable(c_T1_tableStr));
        bI_sp_T21 =  net.addTransition(0,parser.parseTable(bI_sp_T21_tableStr));
        c_T2 =  net.addTransition(0,parser.parseTable(c_T2_tableStr));
        c_T3 =  net.addTransition(0,parser.parseTable(c_T3_tableStr));
        c_T4 =  net.addTransition(0,parser.parseTable(c_T4_tableStr));
        c_T5 =  net.addTransition(1,parser.parseTable(c_T5_tableStr));
        bI_oT5 =  net.addTransition(0,parser.parseTable(bI_oT5_tableStr));
        bI_sp_T22 =  net.addTransition(0,parser.parseTable(bI_sp_T22_tableStr));
        T21 =  net.addTransition(0,parser.parseTable(T21_tableStr));
        bI_oT7 =  net.addTransition(0,parser.parseTable(bI_oT7_tableStr));
        T20 =  net.addTransition(0,parser.parseTable(T20_tableStr));
        bI_oT6 =  net.addTransition(0,parser.parseTable(bI_oT6_tableStr));
        T23 =  net.addTransition(0,parser.parseTable(T23_tableStr));
        T22 =  net.addTransition(0,parser.parseTable(T22_tableStr));
        c_T11 =  net.addTransition(1,parser.parseTable(c_T11_tableStr));
        c_T10 =  net.addTransition(0,parser.parseTable(c_T10_tableStr));
        bI_sp_T1 =  net.addTransition(0,parser.parseTable(bI_sp_T1_tableStr));
        bI_sp_T0 =  net.addTransition(0,parser.parseTable(bI_sp_T0_tableStr));
        c_T20 =  net.addTransition(0,parser.parseTable(c_T20_tableStr));
        c_T22 =  net.addTransition(0,parser.parseTable(c_T22_tableStr));
        c_T21 =  net.addTransition(0,parser.parseTable(c_T21_tableStr));
        bIV_sp_oT0 =  net.addTransition(0,parser.parseTable(bIV_sp_oT0_tableStr));
        bIII_sp_T22 =  net.addTransition(0,parser.parseTable(bIII_sp_T22_tableStr));
        bII_sp_T21 =  net.addTransition(0,parser.parseTable(bII_sp_T21_tableStr));
        bII_sp_T22 =  net.addTransition(0,parser.parseTable(bII_sp_T22_tableStr));
        bIII_sp_T21 =  net.addTransition(0,parser.parseTable(bIII_sp_T21_tableStr));
        bIV_sp_oT1 =  net.addTransition(0,parser.parseTable(bIV_sp_oT1_tableStr));
        bII_sp_T12 =  net.addTransition(0,parser.parseTable(bII_sp_T12_tableStr));
        bIII_oT5 =  net.addTransition(0,parser.parseTable(bIII_oT5_tableStr));
        bI_T3 =  net.addTransition(0,parser.parseTable(bI_T3_tableStr));
        bI_T4 =  net.addTransition(0,parser.parseTable(bI_T4_tableStr));
        bI_T1 =  net.addTransition(0,parser.parseTable(bI_T1_tableStr));
        bI_T2 =  net.addTransition(0,parser.parseTable(bI_T2_tableStr));
        bII_sp_T11 =  net.addTransition(0,parser.parseTable(bII_sp_T11_tableStr));
        bII_sp_T0 =  net.addTransition(0,parser.parseTable(bII_sp_T0_tableStr));
        bII_sp_T1 =  net.addTransition(0,parser.parseTable(bII_sp_T1_tableStr));
        bI_sp_oT0 =  net.addTransition(0,parser.parseTable(bI_sp_oT0_tableStr));
        bI_sp_oT1 =  net.addTransition(0,parser.parseTable(bI_sp_oT1_tableStr));
        bIII_oT7 =  net.addTransition(0,parser.parseTable(bIII_oT7_tableStr));
        bIII_oT6 =  net.addTransition(0,parser.parseTable(bIII_oT6_tableStr));
        nameStore.addTransitionName(c_T50, "c_T50" );
        nameStore.addTransitionName(bII_oT7, "bII_oT7" );
        nameStore.addTransitionName(bII_oT5, "bII_oT5" );
        nameStore.addTransitionName(bII_oT6, "bII_oT6" );
        nameStore.addTransitionName(bIV_sp_T21, "bIV_sp_T21" );
        nameStore.addTransitionName(c_oT0, "c_oT0" );
        nameStore.addTransitionName(c_oT1, "c_oT1" );
        nameStore.addTransitionName(bIV_sp_T22, "bIV_sp_T22" );
        nameStore.addTransitionName(c_oT2, "c_oT2" );
        nameStore.addTransitionName(c_oT3, "c_oT3" );
        nameStore.addTransitionName(bIV_oT6, "bIV_oT6" );
        nameStore.addTransitionName(bIV_oT7, "bIV_oT7" );
        nameStore.addTransitionName(bIII_sp_oT1, "bIII_sp_oT1" );
        nameStore.addTransitionName(bII_sp_oT0, "bII_sp_oT0" );
        nameStore.addTransitionName(bIII_T2, "bIII_T2" );
        nameStore.addTransitionName(bIII_sp_oT0, "bIII_sp_oT0" );
        nameStore.addTransitionName(bII_sp_oT1, "bII_sp_oT1" );
        nameStore.addTransitionName(bIII_T1, "bIII_T1" );
        nameStore.addTransitionName(bIII_T4, "bIII_T4" );
        nameStore.addTransitionName(bIII_T3, "bIII_T3" );
        nameStore.addTransitionName(bIV_oT5, "bIV_oT5" );
        nameStore.addTransitionName(bII_T4, "bII_T4" );
        nameStore.addTransitionName(bII_T2, "bII_T2" );
        nameStore.addTransitionName(bII_T3, "bII_T3" );
        nameStore.addTransitionName(bII_T1, "bII_T1" );
        nameStore.addTransitionName(bIV_sp_T11, "bIV_sp_T11" );
        nameStore.addTransitionName(bIV_sp_T12, "bIV_sp_T12" );
        nameStore.addTransitionName(T0, "T0" );
        nameStore.addTransitionName(T1, "T1" );
        nameStore.addTransitionName(T2, "T2" );
        nameStore.addTransitionName(T3, "T3" );
        nameStore.addTransitionName(T4, "T4" );
        nameStore.addTransitionName(T5, "T5" );
        nameStore.addTransitionName(T6, "T6" );
        nameStore.addTransitionName(T7, "T7" );
        nameStore.addTransitionName(T8, "T8" );
        nameStore.addTransitionName(T9, "T9" );
        nameStore.addTransitionName(bIII_sp_T11, "bIII_sp_T11" );
        nameStore.addTransitionName(bIII_sp_T12, "bIII_sp_T12" );
        nameStore.addTransitionName(bI_sp_T12, "bI_sp_T12" );
        nameStore.addTransitionName(bI_sp_T11, "bI_sp_T11" );
        nameStore.addTransitionName(bIII_sp_T1, "bIII_sp_T1" );
        nameStore.addTransitionName(bIV_T3, "bIV_T3" );
        nameStore.addTransitionName(bIII_sp_T0, "bIII_sp_T0" );
        nameStore.addTransitionName(bIV_T4, "bIV_T4" );
        nameStore.addTransitionName(T10, "T10" );
        nameStore.addTransitionName(bIV_T1, "bIV_T1" );
        nameStore.addTransitionName(bIV_T2, "bIV_T2" );
        nameStore.addTransitionName(T12, "T12" );
        nameStore.addTransitionName(T11, "T11" );
        nameStore.addTransitionName(bIV_sp_T1, "bIV_sp_T1" );
        nameStore.addTransitionName(bIV_sp_T0, "bIV_sp_T0" );
        nameStore.addTransitionName(c_T0, "c_T0" );
        nameStore.addTransitionName(c_T1, "c_T1" );
        nameStore.addTransitionName(bI_sp_T21, "bI_sp_T21" );
        nameStore.addTransitionName(c_T2, "c_T2" );
        nameStore.addTransitionName(c_T3, "c_T3" );
        nameStore.addTransitionName(c_T4, "c_T4" );
        nameStore.addTransitionName(c_T5, "c_T5" );
        nameStore.addTransitionName(bI_oT5, "bI_oT5" );
        nameStore.addTransitionName(bI_sp_T22, "bI_sp_T22" );
        nameStore.addTransitionName(T21, "T21" );
        nameStore.addTransitionName(bI_oT7, "bI_oT7" );
        nameStore.addTransitionName(T20, "T20" );
        nameStore.addTransitionName(bI_oT6, "bI_oT6" );
        nameStore.addTransitionName(T23, "T23" );
        nameStore.addTransitionName(T22, "T22" );
        nameStore.addTransitionName(c_T11, "c_T11" );
        nameStore.addTransitionName(c_T10, "c_T10" );
        nameStore.addTransitionName(bI_sp_T1, "bI_sp_T1" );
        nameStore.addTransitionName(bI_sp_T0, "bI_sp_T0" );
        nameStore.addTransitionName(c_T20, "c_T20" );
        nameStore.addTransitionName(c_T22, "c_T22" );
        nameStore.addTransitionName(c_T21, "c_T21" );
        nameStore.addTransitionName(bIV_sp_oT0, "bIV_sp_oT0" );
        nameStore.addTransitionName(bIII_sp_T22, "bIII_sp_T22" );
        nameStore.addTransitionName(bII_sp_T21, "bII_sp_T21" );
        nameStore.addTransitionName(bII_sp_T22, "bII_sp_T22" );
        nameStore.addTransitionName(bIII_sp_T21, "bIII_sp_T21" );
        nameStore.addTransitionName(bIV_sp_oT1, "bIV_sp_oT1" );
        nameStore.addTransitionName(bII_sp_T12, "bII_sp_T12" );
        nameStore.addTransitionName(bIII_oT5, "bIII_oT5" );
        nameStore.addTransitionName(bI_T3, "bI_T3" );
        nameStore.addTransitionName(bI_T4, "bI_T4" );
        nameStore.addTransitionName(bI_T1, "bI_T1" );
        nameStore.addTransitionName(bI_T2, "bI_T2" );
        nameStore.addTransitionName(bII_sp_T11, "bII_sp_T11" );
        nameStore.addTransitionName(bII_sp_T0, "bII_sp_T0" );
        nameStore.addTransitionName(bII_sp_T1, "bII_sp_T1" );
        nameStore.addTransitionName(bI_sp_oT0, "bI_sp_oT0" );
        nameStore.addTransitionName(bI_sp_oT1, "bI_sp_oT1" );
        nameStore.addTransitionName(bIII_oT7, "bIII_oT7" );
        nameStore.addTransitionName(bIII_oT6, "bIII_oT6" );
    }

    private void addArcs(){
        net.addArcFromPlaceToTransition(P52, oT6, 1.0);
        net.addArcFromPlaceToTransition(P51, oT5, 1.0);
        net.addArcFromPlaceToTransition(P53, oT7, 1.0);
        net.addArcFromPlaceToTransition(_P5, c_T50, 1.0);
        net.addArcFromPlaceToTransition(_P91, bII_oT7, 1.0);
        net.addArcFromPlaceToTransition(_P92, bII_oT5, 1.0);
        net.addArcFromPlaceToTransition(_P90, bII_oT6, 1.0);
        net.addArcFromPlaceToTransition(_P27, bIV_sp_T21, 1.0);
        net.addArcFromPlaceToTransition(_P28, bIV_sp_T21, 1.0);
        net.addArcFromPlaceToTransition(_P70, c_oT0, 1.0);
        net.addArcFromPlaceToTransition(_P74, c_oT1, 1.0);
        net.addArcFromPlaceToTransition(_P26, bIV_sp_T22, 1.0);
        net.addArcFromPlaceToTransition(_P25, bIV_sp_T22, 1.0);
        net.addArcFromPlaceToTransition(_P78, c_oT2, 1.0);
        net.addArcFromPlaceToTransition(_P76, c_oT3, 1.0);
        net.addArcFromPlaceToTransition(_P107, bIV_oT6, 1.0);
        net.addArcFromPlaceToTransition(_P109, bIV_oT7, 1.0);
        net.addArcFromPlaceToTransition(_P85, bIII_sp_oT1, 1.0);
        net.addArcFromPlaceToTransition(_P81, bII_sp_oT0, 1.0);
        net.addArcFromPlaceToTransition(_P75, bIII_T2, 1.0);
        net.addArcFromPlaceToTransition(_P87, bIII_T2, 1.0);
        net.addArcFromPlaceToTransition(_P80, bIII_sp_oT0, 1.0);
        net.addArcFromPlaceToTransition(_P83, bII_sp_oT1, 1.0);
        net.addArcFromPlaceToTransition(_P75, bIII_T1, 1.0);
        net.addArcFromPlaceToTransition(_P88, bIII_T1, 1.0);
        net.addArcFromPlaceToTransition(_P89, bIII_T4, 1.0);
        net.addArcFromPlaceToTransition(_P86, bIII_T3, 0.1);
        net.addArcFromPlaceToTransition(_P75, bIII_T3, 1.0);
        net.addArcFromPlaceToTransition(_P106, bIV_oT5, 1.0);
        net.addArcFromPlaceToTransition(_P9, bII_T4, 1.0);
        net.addArcFromPlaceToTransition(_P93, bII_T2, 1.0);
        net.addArcFromPlaceToTransition(_P7, bII_T2, 1.0);
        net.addArcFromPlaceToTransition(_P8, bII_T3, 0.1);
        net.addArcFromPlaceToTransition(_P93, bII_T3, 1.0);
        net.addArcFromPlaceToTransition(_P93, bII_T1, 1.0);
        net.addArcFromPlaceToTransition(_P6, bII_T1, 1.0);
        net.addArcFromPlaceToTransition(_P95, bIV_sp_T11, 1.0);
        net.addArcFromPlaceToTransition(_P94, bIV_sp_T11, 1.0);
        net.addArcFromPlaceToTransition(_P97, bIV_sp_T12, 1.0);
        net.addArcFromPlaceToTransition(_P96, bIV_sp_T12, 1.0);
        net.addArcFromPlaceToTransition(iP0, T0, 1.0);
        net.addArcFromPlaceToTransition(iP1, T1, 1.0);
        net.addArcFromPlaceToTransition(iP2, T2, 1.0);
        net.addArcFromPlaceToTransition(iP3, T3, 1.0);
        net.addArcFromPlaceToTransition(iP4, T4, 1.0);
        net.addArcFromPlaceToTransition(iP5, T5, 1.0);
        net.addArcFromPlaceToTransition(iP6, T6, 1.0);
        net.addArcFromPlaceToTransition(iP7, T7, 1.0);
        net.addArcFromPlaceToTransition(iP8, T8, 1.0);
        net.addArcFromPlaceToTransition(iP9, T9, 1.0);
        net.addArcFromPlaceToTransition(_P111, bIII_sp_T11, 1.0);
        net.addArcFromPlaceToTransition(_P110, bIII_sp_T11, 1.0);
        net.addArcFromPlaceToTransition(_P113, bIII_sp_T12, 1.0);
        net.addArcFromPlaceToTransition(_P112, bIII_sp_T12, 1.0);
        net.addArcFromPlaceToTransition(_P15, bI_sp_T12, 1.0);
        net.addArcFromPlaceToTransition(_P14, bI_sp_T12, 1.0);
        net.addArcFromPlaceToTransition(_P13, bI_sp_T11, 1.0);
        net.addArcFromPlaceToTransition(_P12, bI_sp_T11, 1.0);
        net.addArcFromPlaceToTransition(_P16, bIII_sp_T1, 1.0);
        net.addArcFromPlaceToTransition(_P22, bIV_T3, 0.1);
        net.addArcFromPlaceToTransition(_P108, bIV_T3, 1.0);
        net.addArcFromPlaceToTransition(_P18, bIII_sp_T0, 0.5);
        net.addArcFromPlaceToTransition(_P23, bIV_T4, 1.0);
        net.addArcFromPlaceToTransition(iP10, T10, 1.0);
        net.addArcFromPlaceToTransition(_P108, bIV_T1, 1.0);
        net.addArcFromPlaceToTransition(_P20, bIV_T1, 1.0);
        net.addArcFromPlaceToTransition(_P108, bIV_T2, 1.0);
        net.addArcFromPlaceToTransition(_P21, bIV_T2, 1.0);
        net.addArcFromPlaceToTransition(iP12, T12, 1.0);
        net.addArcFromPlaceToTransition(iP11, T11, 1.0);
        net.addArcFromPlaceToTransition(_P82, bIV_sp_T1, 1.0);
        net.addArcFromPlaceToTransition(_P84, bIV_sp_T0, 0.5);
        net.addArcFromPlaceToTransition(_P11, c_T0, 0.916666667);
        net.addArcFromPlaceToTransition(_P119, c_T0, 1.0);
        net.addArcFromPlaceToTransition(_P121, c_T1, 1.0);
        net.addArcFromPlaceToTransition(_P35, bI_sp_T21, 1.0);
        net.addArcFromPlaceToTransition(_P36, bI_sp_T21, 1.0);
        net.addArcFromPlaceToTransition(_P125, c_T2, 1.0);
        net.addArcFromPlaceToTransition(_P123, c_T2, 1.0);
        net.addArcFromPlaceToTransition(_P127, c_T3, 1.0);
        net.addArcFromPlaceToTransition(_P125, c_T3, 1.0);
        net.addArcFromPlaceToTransition(_P1, c_T4, 1.0);
        net.addArcFromPlaceToTransition(_P3, c_T5, 1.0);
        net.addArcFromPlaceToTransition(_P29, bI_oT5, 1.0);
        net.addArcFromPlaceToTransition(_P34, bI_sp_T22, 1.0);
        net.addArcFromPlaceToTransition(_P33, bI_sp_T22, 1.0);
        net.addArcFromPlaceToTransition(P24, T21, 1.0);
        net.addArcFromPlaceToTransition(P26, T21, 1.0);
        net.addArcFromPlaceToTransition(_P32, bI_oT7, 1.0);
        net.addArcFromPlaceToTransition(P21, T20, 1.0);
        net.addArcFromPlaceToTransition(P23, T20, 1.0);
        net.addArcFromPlaceToTransition(_P30, bI_oT6, 1.0);
        net.addArcFromPlaceToTransition(P30, T23, 1.0);
        net.addArcFromPlaceToTransition(P32, T23, 1.0);
        net.addArcFromPlaceToTransition(P27, T22, 1.0);
        net.addArcFromPlaceToTransition(P29, T22, 1.0);
        net.addArcFromPlaceToTransition(_P63, c_T11, 1.0);
        net.addArcFromPlaceToTransition(_P65, c_T11, 1.0);
        net.addArcFromPlaceToTransition(_P64, c_T10, 1.0);
        net.addArcFromPlaceToTransition(_P63, c_T10, 1.0);
        net.addArcFromPlaceToTransition(_P114, bI_sp_T1, 1.0);
        net.addArcFromPlaceToTransition(_P115, bI_sp_T0, 0.5);
        net.addArcFromPlaceToTransition(_P65, c_T20, 1.0);
        net.addArcFromPlaceToTransition(_P66, c_T20, 1.0);
        net.addArcFromPlaceToTransition(_P72, c_T22, 1.0);
        net.addArcFromPlaceToTransition(_P71, c_T21, 1.0);
        net.addArcFromPlaceToTransition(_P24, bIV_sp_oT0, 1.0);
        net.addArcFromPlaceToTransition(_P38, bIII_sp_T22, 1.0);
        net.addArcFromPlaceToTransition(_P37, bIII_sp_T22, 1.0);
        net.addArcFromPlaceToTransition(_P98, bII_sp_T21, 1.0);
        net.addArcFromPlaceToTransition(_P103, bII_sp_T21, 1.0);
        net.addArcFromPlaceToTransition(_P101, bII_sp_T22, 1.0);
        net.addArcFromPlaceToTransition(_P105, bII_sp_T22, 1.0);
        net.addArcFromPlaceToTransition(_P39, bIII_sp_T21, 1.0);
        net.addArcFromPlaceToTransition(_P40, bIII_sp_T21, 1.0);
        net.addArcFromPlaceToTransition(_P10, bIV_sp_oT1, 1.0);
        net.addArcFromPlaceToTransition(_P46, bII_sp_T12, 1.0);
        net.addArcFromPlaceToTransition(_P47, bII_sp_T12, 1.0);
        net.addArcFromPlaceToTransition(_P79, bIII_oT5, 1.0);
        net.addArcFromPlaceToTransition(_P49, bI_T3, 0.1);
        net.addArcFromPlaceToTransition(_P31, bI_T3, 1.0);
        net.addArcFromPlaceToTransition(_P54, bI_T4, 1.0);
        net.addArcFromPlaceToTransition(_P31, bI_T1, 1.0);
        net.addArcFromPlaceToTransition(_P51, bI_T1, 1.0);
        net.addArcFromPlaceToTransition(_P31, bI_T2, 1.0);
        net.addArcFromPlaceToTransition(_P50, bI_T2, 1.0);
        net.addArcFromPlaceToTransition(_P44, bII_sp_T11, 1.0);
        net.addArcFromPlaceToTransition(_P45, bII_sp_T11, 1.0);
        net.addArcFromPlaceToTransition(_P17, bII_sp_T0, 0.5);
        net.addArcFromPlaceToTransition(_P19, bII_sp_T1, 1.0);
        net.addArcFromPlaceToTransition(_P48, bI_sp_oT0, 1.0);
        net.addArcFromPlaceToTransition(_P52, bI_sp_oT1, 1.0);
        net.addArcFromPlaceToTransition(P22, oT0, 1.0);
        net.addArcFromPlaceToTransition(_P73, bIII_oT7, 1.0);
        net.addArcFromPlaceToTransition(_P77, bIII_oT6, 1.0);
        net.addArcFromPlaceToTransition(P28, oT2, 1.0);
        net.addArcFromPlaceToTransition(P25, oT1, 1.0);
        net.addArcFromPlaceToTransition(P50, oT4, 1.0);
        net.addArcFromPlaceToTransition(P31, oT3, 1.0);
        net.addArcFromTransitionToPlace(c_T50, _P123);
        net.addArcFromTransitionToPlace(c_T50, _P64);
        net.addArcFromTransitionToPlace(bII_oT7, P53);
        net.addArcFromTransitionToPlace(bII_oT5, P24);
        net.addArcFromTransitionToPlace(bII_oT6, P23);
        net.addArcFromTransitionToPlace(bIV_sp_T21, _P24);
        net.addArcFromTransitionToPlace(c_oT0, _P49);
        net.addArcFromTransitionToPlace(c_oT1, _P86);
        net.addArcFromTransitionToPlace(bIV_sp_T22, _P10);
        net.addArcFromTransitionToPlace(c_oT2, _P8);
        net.addArcFromTransitionToPlace(c_oT3, _P22);
        net.addArcFromTransitionToPlace(bIV_oT6, P29);
        net.addArcFromTransitionToPlace(bIV_oT7, P51);
        net.addArcFromTransitionToPlace(bIII_sp_oT1, _P77);
        net.addArcFromTransitionToPlace(bII_sp_oT0, _P92);
        net.addArcFromTransitionToPlace(bIII_T2, _P75);
        net.addArcFromTransitionToPlace(bIII_T2, _P73);
        net.addArcFromTransitionToPlace(bIII_sp_oT0, _P79);
        net.addArcFromTransitionToPlace(bII_sp_oT1, _P90);
        net.addArcFromTransitionToPlace(bIII_T1, _P75);
        net.addArcFromTransitionToPlace(bIII_T4, _P16);
        net.addArcFromTransitionToPlace(bIII_T3, _P75);
        net.addArcFromTransitionToPlace(bIII_T3, _P18);
        net.addArcFromTransitionToPlace(bIV_oT5, P30);
        net.addArcFromTransitionToPlace(bII_T4, _P19);
        net.addArcFromTransitionToPlace(bII_T2, _P93);
        net.addArcFromTransitionToPlace(bII_T2, _P91);
        net.addArcFromTransitionToPlace(bII_T3, _P93);
        net.addArcFromTransitionToPlace(bII_T3, _P17);
        net.addArcFromTransitionToPlace(bII_T1, _P93);
        net.addArcFromTransitionToPlace(bIV_sp_T11, _P27);
        net.addArcFromTransitionToPlace(bIV_sp_T11, _P25);
        net.addArcFromTransitionToPlace(bIV_sp_T12, _P28);
        net.addArcFromTransitionToPlace(bIV_sp_T12, _P26);
        net.addArcFromTransitionToPlace(T0, _P51);
        net.addArcFromTransitionToPlace(T1, _P6);
        net.addArcFromTransitionToPlace(T2, _P88);
        net.addArcFromTransitionToPlace(T3, _P20);
        net.addArcFromTransitionToPlace(T4, _P54);
        net.addArcFromTransitionToPlace(T5, _P9);
        net.addArcFromTransitionToPlace(T6, _P89);
        net.addArcFromTransitionToPlace(T7, _P23);
        net.addArcFromTransitionToPlace(T8, _P50);
        net.addArcFromTransitionToPlace(T9, _P7);
        net.addArcFromTransitionToPlace(bIII_sp_T11, _P39);
        net.addArcFromTransitionToPlace(bIII_sp_T11, _P37);
        net.addArcFromTransitionToPlace(bIII_sp_T12, _P40);
        net.addArcFromTransitionToPlace(bIII_sp_T12, _P38);
        net.addArcFromTransitionToPlace(bI_sp_T12, _P36);
        net.addArcFromTransitionToPlace(bI_sp_T12, _P34);
        net.addArcFromTransitionToPlace(bI_sp_T11, _P35);
        net.addArcFromTransitionToPlace(bI_sp_T11, _P33);
        net.addArcFromTransitionToPlace(bIII_sp_T1, _P112);
        net.addArcFromTransitionToPlace(bIII_sp_T1, _P110);
        net.addArcFromTransitionToPlace(bIV_T3, _P108);
        net.addArcFromTransitionToPlace(bIV_T3, _P84);
        net.addArcFromTransitionToPlace(bIII_sp_T0, _P111);
        net.addArcFromTransitionToPlace(bIII_sp_T0, _P113);
        net.addArcFromTransitionToPlace(bIV_T4, _P82);
        net.addArcFromTransitionToPlace(T10, _P87);
        net.addArcFromTransitionToPlace(bIV_T1, _P108);
        net.addArcFromTransitionToPlace(bIV_T2, _P108);
        net.addArcFromTransitionToPlace(bIV_T2, _P109);
        net.addArcFromTransitionToPlace(T12, _P11);
        net.addArcFromTransitionToPlace(T11, _P21);
        net.addArcFromTransitionToPlace(bIV_sp_T1, _P96);
        net.addArcFromTransitionToPlace(bIV_sp_T1, _P94);
        net.addArcFromTransitionToPlace(bIV_sp_T0, _P95);
        net.addArcFromTransitionToPlace(bIV_sp_T0, _P97);
        net.addArcFromTransitionToPlace(c_T0, _P5);
        net.addArcFromTransitionToPlace(c_T0, _P121);
        net.addArcFromTransitionToPlace(c_T1, _P119);
        net.addArcFromTransitionToPlace(bI_sp_T21, _P48);
        net.addArcFromTransitionToPlace(c_T2, _P125);
        net.addArcFromTransitionToPlace(c_T3, _P127);
        net.addArcFromTransitionToPlace(c_T3, _P1);
        net.addArcFromTransitionToPlace(c_T4, _P66);
        net.addArcFromTransitionToPlace(c_T4, _P3);
        net.addArcFromTransitionToPlace(c_T5, _P125);
        net.addArcFromTransitionToPlace(bI_oT5, P21);
        net.addArcFromTransitionToPlace(bI_sp_T22, _P52);
        net.addArcFromTransitionToPlace(T21, P25);
        net.addArcFromTransitionToPlace(bI_oT7, P52);
        net.addArcFromTransitionToPlace(T20, P22);
        net.addArcFromTransitionToPlace(bI_oT6, P32);
        net.addArcFromTransitionToPlace(T23, P31);
        net.addArcFromTransitionToPlace(T22, P28);
        net.addArcFromTransitionToPlace(c_T11, _P65);
        net.addArcFromTransitionToPlace(c_T11, _P63);
        net.addArcFromTransitionToPlace(c_T10, _P63);
        net.addArcFromTransitionToPlace(bI_sp_T1, _P14);
        net.addArcFromTransitionToPlace(bI_sp_T1, _P12);
        net.addArcFromTransitionToPlace(bI_sp_T0, _P13);
        net.addArcFromTransitionToPlace(bI_sp_T0, _P15);
        net.addArcFromTransitionToPlace(c_T20, _P71);
        net.addArcFromTransitionToPlace(c_T20, _P72);
        net.addArcFromTransitionToPlace(c_T22, _P78);
        net.addArcFromTransitionToPlace(c_T22, _P76);
        net.addArcFromTransitionToPlace(c_T21, _P70);
        net.addArcFromTransitionToPlace(c_T21, _P74);
        net.addArcFromTransitionToPlace(bIV_sp_oT0, _P106);
        net.addArcFromTransitionToPlace(bIII_sp_T22, _P85);
        net.addArcFromTransitionToPlace(bII_sp_T21, _P81);
        net.addArcFromTransitionToPlace(bII_sp_T22, _P83);
        net.addArcFromTransitionToPlace(bIII_sp_T21, _P80);
        net.addArcFromTransitionToPlace(bIV_sp_oT1, _P107);
        net.addArcFromTransitionToPlace(bII_sp_T12, _P103);
        net.addArcFromTransitionToPlace(bII_sp_T12, _P101);
        net.addArcFromTransitionToPlace(bIII_oT5, P27);
        net.addArcFromTransitionToPlace(bI_T3, _P31);
        net.addArcFromTransitionToPlace(bI_T3, _P115);
        net.addArcFromTransitionToPlace(bI_T4, _P114);
        net.addArcFromTransitionToPlace(bI_T1, _P31);
        net.addArcFromTransitionToPlace(bI_T2, _P31);
        net.addArcFromTransitionToPlace(bI_T2, _P32);
        net.addArcFromTransitionToPlace(bII_sp_T11, _P98);
        net.addArcFromTransitionToPlace(bII_sp_T11, _P105);
        net.addArcFromTransitionToPlace(bII_sp_T0, _P44);
        net.addArcFromTransitionToPlace(bII_sp_T0, _P46);
        net.addArcFromTransitionToPlace(bII_sp_T1, _P47);
        net.addArcFromTransitionToPlace(bII_sp_T1, _P45);
        net.addArcFromTransitionToPlace(bI_sp_oT0, _P29);
        net.addArcFromTransitionToPlace(bI_sp_oT1, _P30);
        net.addArcFromTransitionToPlace(bIII_oT7, P50);
        net.addArcFromTransitionToPlace(bIII_oT6, P26);
    }

    private void putInitialMarking(){
        net.setInitialMarkingForPlace(_P119, FuzzyToken.buildFromString("<0.00,0.00,1.00,0.00,0.00>"));
        net.setInitialMarkingForPlace(_P125, FuzzyToken.buildFromString("<0.00,0.00,1.00,0.00,0.00>"));
        net.setInitialMarkingForPlace(_P127, FuzzyToken.buildFromString("<0.00,0.00,5.00,1.00,0.00>"));
    }

}