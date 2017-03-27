import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class IntersectionUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0, iP1, iP2, iP66, iP67, iP149, iP153, iP155, iP157, iP160, iP161, iP162 ;
  /*ordinary places */
  public int P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, P21, P22, P23, P24, P25, P26, P27, P28, P29, P30, P31, P32, P33, P34, P35, P36, P37, P38, P39, P40, P41, P42, P43, P44, P45, P46, P47, P48, P49, P50, P51, P52, P53, P54, P55, P56, P57, P58, P59, P60, P61, P62, P63, P64, P65, P68, P69, P70, P71, P72, P73, P74, P75, P76, P77, P78, P79, P80, P81, P82, P83, P84, P85, P86, P87, P88, P89, P90, P91, P92, P93, P94, P95, P96, P97, P98, P99, P100, P101, P102, P103, P104, P105, P106, P107, P108, P109, P110, P111, P112, P113, P114, P115, P116, P117, P118, P119, P120, P121, P122, P123, P124, P125, P126, P127, P128, P129, P130, P131, P132, P133, P134, P135, P136, P137, P138, P139, P140, P141, P142, P143, P144, P145, P146, P147, P148, P150, P151, P152, P154, P156, P158, P159, P163 ;
  /* output transitions */
  public int oT127, oT128, oT129, oT130 ;
  /* table for out transitions */
  String oT127_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT128_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT129_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT130_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1, T2, T3, T4_d1, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32, T33_d1, T34, T35, T36, T37, T38, T39, T40, T41, T42, T43, T44, T45, T46, T47, T48, T49, T50, T51, T52, T53, T54, T55, T56, T57, T58, T59, T60, T61, T62, T63, T64, T65, T66, T67, T68_d1, T69, T70, T71, T72, T73, T74_d1, T75, T76, T77, T78, T79, T80, T81, T82, T83, T84, T85, T86, T87, T88, T89, T90, T91, T92, T93, T94, T95, T96, T97, T98, T99, T100, T101, T102, T103, T104, T105, T106, T107, T108, T109, T110, T111, T112, T113, T114, T115, T116, T117, T118, T119, T120, T121, T122, T123, T124, T125, T126, T131 ;
  /* table for delay transitions */
  String T0_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T4_d1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T5_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T6_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T7_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T8_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T9_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T10_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T11_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T12_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T13_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T14_tableStr = "{"+
  	"[<-2,-2><-2,-2><-2,-2><-2,-2><-2,-2><FF,FF>]"+//
  	"[<-1,-1><-1,-1><-1,-1><-1,-1><-1,-1><FF,FF>]"+//
  	"[< 0, 0>< 0, 0>< 0, 0>< 0, 0>< 0, 0><FF,FF>]"+//
  	"[< 1, 1>< 1, 1>< 1, 1>< 1, 1>< 1, 1><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T15_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T16_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T17_tableStr = "{"+
  	"[<-2><-2><-2><-2><-2><FF>]"+//
  	"[<-2><-1><-1><-1><-1><FF>]"+//
  	"[<-2><-1>< 0>< 0>< 0><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T18_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T19_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T20_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T21_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T22_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T23_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T24_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T25_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T26_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T27_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T28_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T29_tableStr = "{"+
  	"[<-2,-2><-2,-2><-2,-2><-2,-2><-2,-2><FF,FF>]"+//
  	"[<-1,-1><-1,-1><-1,-1><-1,-1><-1,-1><FF,FF>]"+//
  	"[< 0, 0>< 0, 0>< 0, 0>< 0, 0>< 0, 0><FF,FF>]"+//
  	"[< 1, 1>< 1, 1>< 1, 1>< 1, 1>< 1, 1><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T30_tableStr = "{"+
  	"[<-2><-2><-2><-2><-2><FF>]"+//
  	"[<-2><-1><-1><-1><-1><FF>]"+//
  	"[<-2><-1>< 0>< 0>< 0><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T31_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T32_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T33_d1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T34_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T35_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T36_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T37_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T38_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T39_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T40_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T41_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T42_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T43_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T44_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T45_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T46_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T47_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T48_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T49_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T50_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T51_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T52_tableStr = "{"+
  	"[<-2><-2><-2><-2><-2><FF>]"+//
  	"[<-2><-1><-1><-1><-1><FF>]"+//
  	"[<-2><-1>< 0>< 0>< 0><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T53_tableStr = "{"+
  	"[<-2><-2><-2><-2><-2><FF>]"+//
  	"[<-2><-1><-1><-1><-1><FF>]"+//
  	"[<-2><-1>< 0>< 0>< 0><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T54_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T55_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T56_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T57_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T58_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T59_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T60_tableStr = "{"+
  	"[<-2,-2><-2,-2><-2,-2><-2,-2><-2,-2><FF,FF>]"+//
  	"[<-1,-1><-1,-1><-1,-1><-1,-1><-1,-1><FF,FF>]"+//
  	"[< 0, 0>< 0, 0>< 0, 0>< 0, 0>< 0, 0><FF,FF>]"+//
  	"[< 1, 1>< 1, 1>< 1, 1>< 1, 1>< 1, 1><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T61_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T62_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T63_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T64_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T65_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T66_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T67_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T68_d1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T69_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T70_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T71_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T72_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T73_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T74_d1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T75_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><-2>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><-1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 0>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 2>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  "}";
  String T76_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><-2>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><-1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 0>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 2>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  "}";
  String T77_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><-2>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><-1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 0>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 2>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  "}";
  String T78_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><-2>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><-1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 0>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 2>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  "}";
  String T79_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T80_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T81_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T82_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T83_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T84_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T85_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T86_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T87_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T88_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T89_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T90_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T91_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T92_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T93_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T94_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T95_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T96_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T97_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T98_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T99_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T100_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T101_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T102_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T103_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T104_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T105_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T106_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T107_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T108_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T109_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T110_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T111_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T112_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T113_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T114_tableStr = "{"+
  	"[<-2,-2><-2,-2><-2,-2><-2,-2><-2,-2><FF,FF>]"+//
  	"[<-1,-1><-1,-1><-1,-1><-1,-1><-1,-1><FF,FF>]"+//
  	"[< 0, 0>< 0, 0>< 0, 0>< 0, 0>< 0, 0><FF,FF>]"+//
  	"[< 1, 1>< 1, 1>< 1, 1>< 1, 1>< 1, 1><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T115_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T116_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T117_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T118_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T119_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T120_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T121_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T122_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T123_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T124_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T125_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T126_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T131_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public IntersectionUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(50.0);
    iP1 = net.addInputPlace(1.0);
    iP2 = net.addInputPlace(50.0);
    iP66 = net.addInputPlace(50.0);
    iP67 = net.addInputPlace(50.0);
    iP149 = net.addInputPlace(0.0);
    iP153 = net.addInputPlace(0.0);
    iP155 = net.addInputPlace(0.0);
    iP157 = net.addInputPlace(1.0);
    iP160 = net.addInputPlace(0.0);
    iP161 = net.addInputPlace(1.0);
    iP162 = net.addInputPlace(1.0);
    P3 = net.addPlace(50.0);
    P4 = net.addPlace(1.0);
    P5 = net.addPlace(50.0);
    P6 = net.addPlace(50.0);
    P7 = net.addPlace(50.0);
    P8 = net.addPlace(50.0);
    P9 = net.addPlace(50.0);
    P10 = net.addPlace(50.0);
    P11 = net.addPlace(0.0);
    P12 = net.addPlace(50.0);
    P13 = net.addPlace(0.0);
    P14 = net.addPlace(1.0);
    P15 = net.addPlace(50.0);
    P16 = net.addPlace(1.0);
    P17 = net.addPlace(50.0);
    P18 = net.addPlace(50.0);
    P19 = net.addPlace(50.0);
    P20 = net.addPlace(1.0);
    P21 = net.addPlace(50.0);
    P22 = net.addPlace(1.0);
    P23 = net.addPlace(0.0);
    P24 = net.addPlace(0.0);
    P25 = net.addPlace(0.0);
    P26 = net.addPlace(1.0);
    P27 = net.addPlace(50.0);
    P28 = net.addPlace(1.0);
    P29 = net.addPlace(50.0);
    P30 = net.addPlace(50.0);
    P31 = net.addPlace(1.0);
    P32 = net.addPlace(50.0);
    P33 = net.addPlace(0.0);
    P34 = net.addPlace(0.0);
    P35 = net.addPlace(1.0);
    P36 = net.addPlace(1.0);
    P37 = net.addPlace(50.0);
    P38 = net.addPlace(50.0);
    P39 = net.addPlace(50.0);
    P40 = net.addPlace(1.0);
    P41 = net.addPlace(50.0);
    P42 = net.addPlace(1.0);
    P43 = net.addPlace(50.0);
    P44 = net.addPlace(50.0);
    P45 = net.addPlace(50.0);
    P46 = net.addPlace(1.0);
    P47 = net.addPlace(1.0);
    P48 = net.addPlace(50.0);
    P49 = net.addPlace(50.0);
    P50 = net.addPlace(50.0);
    P51 = net.addPlace(1.0);
    P52 = net.addPlace(1.0);
    P53 = net.addPlace(50.0);
    P54 = net.addPlace(50.0);
    P55 = net.addPlace(50.0);
    P56 = net.addPlace(50.0);
    P57 = net.addPlace(50.0);
    P58 = net.addPlace(1.0);
    P59 = net.addPlace(50.0);
    P60 = net.addPlace(50.0);
    P61 = net.addPlace(1.0);
    P62 = net.addPlace(50.0);
    P63 = net.addPlace(50.0);
    P64 = net.addPlace(50.0);
    P65 = net.addPlace(50.0);
    P68 = net.addPlace(50.0);
    P69 = net.addPlace(50.0);
    P70 = net.addPlace(50.0);
    P71 = net.addPlace(50.0);
    P72 = net.addPlace(1.0);
    P73 = net.addPlace(1.0);
    P74 = net.addPlace(50.0);
    P75 = net.addPlace(50.0);
    P76 = net.addPlace(1.0);
    P77 = net.addPlace(50.0);
    P78 = net.addPlace(1.0);
    P79 = net.addPlace(50.0);
    P80 = net.addPlace(1.0);
    P81 = net.addPlace(50.0);
    P82 = net.addPlace(50.0);
    P83 = net.addPlace(50.0);
    P84 = net.addPlace(50.0);
    P85 = net.addPlace(50.0);
    P86 = net.addPlace(50.0);
    P87 = net.addPlace(50.0);
    P88 = net.addPlace(50.0);
    P89 = net.addPlace(50.0);
    P90 = net.addPlace(50.0);
    P91 = net.addPlace(50.0);
    P92 = net.addPlace(50.0);
    P93 = net.addPlace(1.0);
    P94 = net.addPlace(0.0);
    P95 = net.addPlace(0.0);
    P96 = net.addPlace(0.0);
    P97 = net.addPlace(50.0);
    P98 = net.addPlace(1.0);
    P99 = net.addPlace(100.0);
    P100 = net.addPlace(100.0);
    P101 = net.addPlace(100.0);
    P102 = net.addPlace(100.0);
    P103 = net.addPlace(50.0);
    P104 = net.addPlace(50.0);
    P105 = net.addPlace(50.0);
    P106 = net.addPlace(0.0);
    P107 = net.addPlace(50.0);
    P108 = net.addPlace(1.0);
    P109 = net.addPlace(50.0);
    P110 = net.addPlace(50.0);
    P111 = net.addPlace(1.0);
    P112 = net.addPlace(50.0);
    P113 = net.addPlace(50.0);
    P114 = net.addPlace(50.0);
    P115 = net.addPlace(50.0);
    P116 = net.addPlace(1.0);
    P117 = net.addPlace(50.0);
    P118 = net.addPlace(50.0);
    P119 = net.addPlace(0.0);
    P120 = net.addPlace(50.0);
    P121 = net.addPlace(0.0);
    P122 = net.addPlace(0.0);
    P123 = net.addPlace(0.0);
    P124 = net.addPlace(0.0);
    P125 = net.addPlace(50.0);
    P126 = net.addPlace(0.0);
    P127 = net.addPlace(50.0);
    P128 = net.addPlace(0.0);
    P129 = net.addPlace(50.0);
    P130 = net.addPlace(0.0);
    P131 = net.addPlace(50.0);
    P132 = net.addPlace(50.0);
    P133 = net.addPlace(0.0);
    P134 = net.addPlace(50.0);
    P135 = net.addPlace(0.0);
    P136 = net.addPlace(0.0);
    P137 = net.addPlace(0.0);
    P138 = net.addPlace(0.0);
    P139 = net.addPlace(0.0);
    P140 = net.addPlace(0.0);
    P141 = net.addPlace(50.0);
    P142 = net.addPlace(50.0);
    P143 = net.addPlace(50.0);
    P144 = net.addPlace(50.0);
    P145 = net.addPlace(50.0);
    P146 = net.addPlace(0.0);
    P147 = net.addPlace(0.0);
    P148 = net.addPlace(0.0);
    P150 = net.addPlace(50.0);
    P151 = net.addPlace(50.0);
    P152 = net.addPlace(0.0);
    P154 = net.addPlace(50.0);
    P156 = net.addPlace(50.0);
    P158 = net.addPlace(0.0);
    P159 = net.addPlace(50.0);
    P163 = net.addPlace(0.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(iP2, "iP2" );
    nameStore.addPlaceName(iP66, "iP66" );
    nameStore.addPlaceName(iP67, "iP67" );
    nameStore.addPlaceName(iP149, "iP149" );
    nameStore.addPlaceName(iP153, "iP153" );
    nameStore.addPlaceName(iP155, "iP155" );
    nameStore.addPlaceName(iP157, "iP157" );
    nameStore.addPlaceName(iP160, "iP160" );
    nameStore.addPlaceName(iP161, "iP161" );
    nameStore.addPlaceName(iP162, "iP162" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P5, "P5" );
    nameStore.addPlaceName(P6, "P6" );
    nameStore.addPlaceName(P7, "P7" );
    nameStore.addPlaceName(P8, "P8" );
    nameStore.addPlaceName(P9, "P9" );
    nameStore.addPlaceName(P10, "P10" );
    nameStore.addPlaceName(P11, "P11" );
    nameStore.addPlaceName(P12, "P12" );
    nameStore.addPlaceName(P13, "P13" );
    nameStore.addPlaceName(P14, "P14" );
    nameStore.addPlaceName(P15, "P15" );
    nameStore.addPlaceName(P16, "P16" );
    nameStore.addPlaceName(P17, "P17" );
    nameStore.addPlaceName(P18, "P18" );
    nameStore.addPlaceName(P19, "P19" );
    nameStore.addPlaceName(P20, "P20" );
    nameStore.addPlaceName(P21, "P21" );
    nameStore.addPlaceName(P22, "P22" );
    nameStore.addPlaceName(P23, "P23" );
    nameStore.addPlaceName(P24, "P24" );
    nameStore.addPlaceName(P25, "P25" );
    nameStore.addPlaceName(P26, "P26" );
    nameStore.addPlaceName(P27, "P27" );
    nameStore.addPlaceName(P28, "P28" );
    nameStore.addPlaceName(P29, "P29" );
    nameStore.addPlaceName(P30, "P30" );
    nameStore.addPlaceName(P31, "P31" );
    nameStore.addPlaceName(P32, "P32" );
    nameStore.addPlaceName(P33, "P33" );
    nameStore.addPlaceName(P34, "P34" );
    nameStore.addPlaceName(P35, "P35" );
    nameStore.addPlaceName(P36, "P36" );
    nameStore.addPlaceName(P37, "P37" );
    nameStore.addPlaceName(P38, "P38" );
    nameStore.addPlaceName(P39, "P39" );
    nameStore.addPlaceName(P40, "P40" );
    nameStore.addPlaceName(P41, "P41" );
    nameStore.addPlaceName(P42, "P42" );
    nameStore.addPlaceName(P43, "P43" );
    nameStore.addPlaceName(P44, "P44" );
    nameStore.addPlaceName(P45, "P45" );
    nameStore.addPlaceName(P46, "P46" );
    nameStore.addPlaceName(P47, "P47" );
    nameStore.addPlaceName(P48, "P48" );
    nameStore.addPlaceName(P49, "P49" );
    nameStore.addPlaceName(P50, "P50" );
    nameStore.addPlaceName(P51, "P51" );
    nameStore.addPlaceName(P52, "P52" );
    nameStore.addPlaceName(P53, "P53" );
    nameStore.addPlaceName(P54, "P54" );
    nameStore.addPlaceName(P55, "P55" );
    nameStore.addPlaceName(P56, "P56" );
    nameStore.addPlaceName(P57, "P57" );
    nameStore.addPlaceName(P58, "P58" );
    nameStore.addPlaceName(P59, "P59" );
    nameStore.addPlaceName(P60, "P60" );
    nameStore.addPlaceName(P61, "P61" );
    nameStore.addPlaceName(P62, "P62" );
    nameStore.addPlaceName(P63, "P63" );
    nameStore.addPlaceName(P64, "P64" );
    nameStore.addPlaceName(P65, "P65" );
    nameStore.addPlaceName(P68, "P68" );
    nameStore.addPlaceName(P69, "P69" );
    nameStore.addPlaceName(P70, "P70" );
    nameStore.addPlaceName(P71, "P71" );
    nameStore.addPlaceName(P72, "P72" );
    nameStore.addPlaceName(P73, "P73" );
    nameStore.addPlaceName(P74, "P74" );
    nameStore.addPlaceName(P75, "P75" );
    nameStore.addPlaceName(P76, "P76" );
    nameStore.addPlaceName(P77, "P77" );
    nameStore.addPlaceName(P78, "P78" );
    nameStore.addPlaceName(P79, "P79" );
    nameStore.addPlaceName(P80, "P80" );
    nameStore.addPlaceName(P81, "P81" );
    nameStore.addPlaceName(P82, "P82" );
    nameStore.addPlaceName(P83, "P83" );
    nameStore.addPlaceName(P84, "P84" );
    nameStore.addPlaceName(P85, "P85" );
    nameStore.addPlaceName(P86, "P86" );
    nameStore.addPlaceName(P87, "P87" );
    nameStore.addPlaceName(P88, "P88" );
    nameStore.addPlaceName(P89, "P89" );
    nameStore.addPlaceName(P90, "P90" );
    nameStore.addPlaceName(P91, "P91" );
    nameStore.addPlaceName(P92, "P92" );
    nameStore.addPlaceName(P93, "P93" );
    nameStore.addPlaceName(P94, "P94" );
    nameStore.addPlaceName(P95, "P95" );
    nameStore.addPlaceName(P96, "P96" );
    nameStore.addPlaceName(P97, "P97" );
    nameStore.addPlaceName(P98, "P98" );
    nameStore.addPlaceName(P99, "P99" );
    nameStore.addPlaceName(P100, "P100" );
    nameStore.addPlaceName(P101, "P101" );
    nameStore.addPlaceName(P102, "P102" );
    nameStore.addPlaceName(P103, "P103" );
    nameStore.addPlaceName(P104, "P104" );
    nameStore.addPlaceName(P105, "P105" );
    nameStore.addPlaceName(P106, "P106" );
    nameStore.addPlaceName(P107, "P107" );
    nameStore.addPlaceName(P108, "P108" );
    nameStore.addPlaceName(P109, "P109" );
    nameStore.addPlaceName(P110, "P110" );
    nameStore.addPlaceName(P111, "P111" );
    nameStore.addPlaceName(P112, "P112" );
    nameStore.addPlaceName(P113, "P113" );
    nameStore.addPlaceName(P114, "P114" );
    nameStore.addPlaceName(P115, "P115" );
    nameStore.addPlaceName(P116, "P116" );
    nameStore.addPlaceName(P117, "P117" );
    nameStore.addPlaceName(P118, "P118" );
    nameStore.addPlaceName(P119, "P119" );
    nameStore.addPlaceName(P120, "P120" );
    nameStore.addPlaceName(P121, "P121" );
    nameStore.addPlaceName(P122, "P122" );
    nameStore.addPlaceName(P123, "P123" );
    nameStore.addPlaceName(P124, "P124" );
    nameStore.addPlaceName(P125, "P125" );
    nameStore.addPlaceName(P126, "P126" );
    nameStore.addPlaceName(P127, "P127" );
    nameStore.addPlaceName(P128, "P128" );
    nameStore.addPlaceName(P129, "P129" );
    nameStore.addPlaceName(P130, "P130" );
    nameStore.addPlaceName(P131, "P131" );
    nameStore.addPlaceName(P132, "P132" );
    nameStore.addPlaceName(P133, "P133" );
    nameStore.addPlaceName(P134, "P134" );
    nameStore.addPlaceName(P135, "P135" );
    nameStore.addPlaceName(P136, "P136" );
    nameStore.addPlaceName(P137, "P137" );
    nameStore.addPlaceName(P138, "P138" );
    nameStore.addPlaceName(P139, "P139" );
    nameStore.addPlaceName(P140, "P140" );
    nameStore.addPlaceName(P141, "P141" );
    nameStore.addPlaceName(P142, "P142" );
    nameStore.addPlaceName(P143, "P143" );
    nameStore.addPlaceName(P144, "P144" );
    nameStore.addPlaceName(P145, "P145" );
    nameStore.addPlaceName(P146, "P146" );
    nameStore.addPlaceName(P147, "P147" );
    nameStore.addPlaceName(P148, "P148" );
    nameStore.addPlaceName(P150, "P150" );
    nameStore.addPlaceName(P151, "P151" );
    nameStore.addPlaceName(P152, "P152" );
    nameStore.addPlaceName(P154, "P154" );
    nameStore.addPlaceName(P156, "P156" );
    nameStore.addPlaceName(P158, "P158" );
    nameStore.addPlaceName(P159, "P159" );
    nameStore.addPlaceName(P163, "P163" );
  }

  private void addTransitions(){
    oT127 =  net.addOuputTransition(parser.parseOneXOneTable(oT127_tableStr));
    oT128 =  net.addOuputTransition(parser.parseOneXOneTable(oT128_tableStr));
    oT129 =  net.addOuputTransition(parser.parseOneXOneTable(oT129_tableStr));
    oT130 =  net.addOuputTransition(parser.parseOneXOneTable(oT130_tableStr));
    nameStore.addTransitionName(oT127, "oT127" );
    nameStore.addTransitionName(oT128, "oT128" );
    nameStore.addTransitionName(oT129, "oT129" );
    nameStore.addTransitionName(oT130, "oT130" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T2 = net.addTransition(0, parser.parseTable(T2_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    T4_d1 = net.addTransition(1, parser.parseTable(T4_d1_tableStr));
    T5 = net.addTransition(0, parser.parseTable(T5_tableStr));
    T6 = net.addTransition(0, parser.parseTable(T6_tableStr));
    T7 = net.addTransition(0, parser.parseTable(T7_tableStr));
    T8 = net.addTransition(0, parser.parseTable(T8_tableStr));
    T9 = net.addTransition(0, parser.parseTable(T9_tableStr));
    T10 = net.addTransition(0, parser.parseTable(T10_tableStr));
    T11 = net.addTransition(0, parser.parseTable(T11_tableStr));
    T12 = net.addTransition(0, parser.parseTable(T12_tableStr));
    T13 = net.addTransition(0, parser.parseTable(T13_tableStr));
    T14 = net.addTransition(0, parser.parseTable(T14_tableStr));
    T15 = net.addTransition(0, parser.parseTable(T15_tableStr));
    T16 = net.addTransition(0, parser.parseTable(T16_tableStr));
    T17 = net.addTransition(0, parser.parseTable(T17_tableStr));
    T18 = net.addTransition(0, parser.parseTable(T18_tableStr));
    T19 = net.addTransition(0, parser.parseTable(T19_tableStr));
    T20 = net.addTransition(0, parser.parseTable(T20_tableStr));
    T21 = net.addTransition(0, parser.parseTable(T21_tableStr));
    T22 = net.addTransition(0, parser.parseTable(T22_tableStr));
    T23 = net.addTransition(0, parser.parseTable(T23_tableStr));
    T24 = net.addTransition(0, parser.parseTable(T24_tableStr));
    T25 = net.addTransition(0, parser.parseTable(T25_tableStr));
    T26 = net.addTransition(0, parser.parseTable(T26_tableStr));
    T27 = net.addTransition(0, parser.parseTable(T27_tableStr));
    T28 = net.addTransition(0, parser.parseTable(T28_tableStr));
    T29 = net.addTransition(0, parser.parseTable(T29_tableStr));
    T30 = net.addTransition(0, parser.parseTable(T30_tableStr));
    T31 = net.addTransition(0, parser.parseTable(T31_tableStr));
    T32 = net.addTransition(0, parser.parseTable(T32_tableStr));
    T33_d1 = net.addTransition(1, parser.parseTable(T33_d1_tableStr));
    T34 = net.addTransition(0, parser.parseTable(T34_tableStr));
    T35 = net.addTransition(0, parser.parseTable(T35_tableStr));
    T36 = net.addTransition(0, parser.parseTable(T36_tableStr));
    T37 = net.addTransition(0, parser.parseTable(T37_tableStr));
    T38 = net.addTransition(0, parser.parseTable(T38_tableStr));
    T39 = net.addTransition(0, parser.parseTable(T39_tableStr));
    T40 = net.addTransition(0, parser.parseTable(T40_tableStr));
    T41 = net.addTransition(0, parser.parseTable(T41_tableStr));
    T42 = net.addTransition(0, parser.parseTable(T42_tableStr));
    T43 = net.addTransition(0, parser.parseTable(T43_tableStr));
    T44 = net.addTransition(0, parser.parseTable(T44_tableStr));
    T45 = net.addTransition(0, parser.parseTable(T45_tableStr));
    T46 = net.addTransition(0, parser.parseTable(T46_tableStr));
    T47 = net.addTransition(0, parser.parseTable(T47_tableStr));
    T48 = net.addTransition(0, parser.parseTable(T48_tableStr));
    T49 = net.addTransition(0, parser.parseTable(T49_tableStr));
    T50 = net.addTransition(0, parser.parseTable(T50_tableStr));
    T51 = net.addTransition(0, parser.parseTable(T51_tableStr));
    T52 = net.addTransition(0, parser.parseTable(T52_tableStr));
    T53 = net.addTransition(0, parser.parseTable(T53_tableStr));
    T54 = net.addTransition(0, parser.parseTable(T54_tableStr));
    T55 = net.addTransition(0, parser.parseTable(T55_tableStr));
    T56 = net.addTransition(0, parser.parseTable(T56_tableStr));
    T57 = net.addTransition(0, parser.parseTable(T57_tableStr));
    T58 = net.addTransition(0, parser.parseTable(T58_tableStr));
    T59 = net.addTransition(0, parser.parseTable(T59_tableStr));
    T60 = net.addTransition(0, parser.parseTable(T60_tableStr));
    T61 = net.addTransition(0, parser.parseTable(T61_tableStr));
    T62 = net.addTransition(0, parser.parseTable(T62_tableStr));
    T63 = net.addTransition(0, parser.parseTable(T63_tableStr));
    T64 = net.addTransition(0, parser.parseTable(T64_tableStr));
    T65 = net.addTransition(0, parser.parseTable(T65_tableStr));
    T66 = net.addTransition(0, parser.parseTable(T66_tableStr));
    T67 = net.addTransition(0, parser.parseTable(T67_tableStr));
    T68_d1 = net.addTransition(1, parser.parseTable(T68_d1_tableStr));
    T69 = net.addTransition(0, parser.parseTable(T69_tableStr));
    T70 = net.addTransition(0, parser.parseTable(T70_tableStr));
    T71 = net.addTransition(0, parser.parseTable(T71_tableStr));
    T72 = net.addTransition(0, parser.parseTable(T72_tableStr));
    T73 = net.addTransition(0, parser.parseTable(T73_tableStr));
    T74_d1 = net.addTransition(1, parser.parseTable(T74_d1_tableStr));
    T75 = net.addTransition(0, parser.parseTable(T75_tableStr));
    T76 = net.addTransition(0, parser.parseTable(T76_tableStr));
    T77 = net.addTransition(0, parser.parseTable(T77_tableStr));
    T78 = net.addTransition(0, parser.parseTable(T78_tableStr));
    T79 = net.addTransition(0, parser.parseTable(T79_tableStr));
    T80 = net.addTransition(0, parser.parseTable(T80_tableStr));
    T81 = net.addTransition(0, parser.parseTable(T81_tableStr));
    T82 = net.addTransition(0, parser.parseTable(T82_tableStr));
    T83 = net.addTransition(0, parser.parseTable(T83_tableStr));
    T84 = net.addTransition(0, parser.parseTable(T84_tableStr));
    T85 = net.addTransition(0, parser.parseTable(T85_tableStr));
    T86 = net.addTransition(0, parser.parseTable(T86_tableStr));
    T87 = net.addTransition(0, parser.parseTable(T87_tableStr));
    T88 = net.addTransition(0, parser.parseTable(T88_tableStr));
    T89 = net.addTransition(0, parser.parseTable(T89_tableStr));
    T90 = net.addTransition(0, parser.parseTable(T90_tableStr));
    T91 = net.addTransition(0, parser.parseTable(T91_tableStr));
    T92 = net.addTransition(0, parser.parseTable(T92_tableStr));
    T93 = net.addTransition(0, parser.parseTable(T93_tableStr));
    T94 = net.addTransition(0, parser.parseTable(T94_tableStr));
    T95 = net.addTransition(0, parser.parseTable(T95_tableStr));
    T96 = net.addTransition(0, parser.parseTable(T96_tableStr));
    T97 = net.addTransition(0, parser.parseTable(T97_tableStr));
    T98 = net.addTransition(0, parser.parseTable(T98_tableStr));
    T99 = net.addTransition(0, parser.parseTable(T99_tableStr));
    T100 = net.addTransition(0, parser.parseTable(T100_tableStr));
    T101 = net.addTransition(0, parser.parseTable(T101_tableStr));
    T102 = net.addTransition(0, parser.parseTable(T102_tableStr));
    T103 = net.addTransition(0, parser.parseTable(T103_tableStr));
    T104 = net.addTransition(0, parser.parseTable(T104_tableStr));
    T105 = net.addTransition(0, parser.parseTable(T105_tableStr));
    T106 = net.addTransition(0, parser.parseTable(T106_tableStr));
    T107 = net.addTransition(0, parser.parseTable(T107_tableStr));
    T108 = net.addTransition(0, parser.parseTable(T108_tableStr));
    T109 = net.addTransition(0, parser.parseTable(T109_tableStr));
    T110 = net.addTransition(0, parser.parseTable(T110_tableStr));
    T111 = net.addTransition(0, parser.parseTable(T111_tableStr));
    T112 = net.addTransition(0, parser.parseTable(T112_tableStr));
    T113 = net.addTransition(0, parser.parseTable(T113_tableStr));
    T114 = net.addTransition(0, parser.parseTable(T114_tableStr));
    T115 = net.addTransition(0, parser.parseTable(T115_tableStr));
    T116 = net.addTransition(0, parser.parseTable(T116_tableStr));
    T117 = net.addTransition(0, parser.parseTable(T117_tableStr));
    T118 = net.addTransition(0, parser.parseTable(T118_tableStr));
    T119 = net.addTransition(0, parser.parseTable(T119_tableStr));
    T120 = net.addTransition(0, parser.parseTable(T120_tableStr));
    T121 = net.addTransition(0, parser.parseTable(T121_tableStr));
    T122 = net.addTransition(0, parser.parseTable(T122_tableStr));
    T123 = net.addTransition(0, parser.parseTable(T123_tableStr));
    T124 = net.addTransition(0, parser.parseTable(T124_tableStr));
    T125 = net.addTransition(0, parser.parseTable(T125_tableStr));
    T126 = net.addTransition(0, parser.parseTable(T126_tableStr));
    T131 = net.addTransition(0, parser.parseTable(T131_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2, "T2" );
    nameStore.addTransitionName(T3, "T3" );
    nameStore.addTransitionName(T4_d1, "T4_d1" );
    nameStore.addTransitionName(T5, "T5" );
    nameStore.addTransitionName(T6, "T6" );
    nameStore.addTransitionName(T7, "T7" );
    nameStore.addTransitionName(T8, "T8" );
    nameStore.addTransitionName(T9, "T9" );
    nameStore.addTransitionName(T10, "T10" );
    nameStore.addTransitionName(T11, "T11" );
    nameStore.addTransitionName(T12, "T12" );
    nameStore.addTransitionName(T13, "T13" );
    nameStore.addTransitionName(T14, "T14" );
    nameStore.addTransitionName(T15, "T15" );
    nameStore.addTransitionName(T16, "T16" );
    nameStore.addTransitionName(T17, "T17" );
    nameStore.addTransitionName(T18, "T18" );
    nameStore.addTransitionName(T19, "T19" );
    nameStore.addTransitionName(T20, "T20" );
    nameStore.addTransitionName(T21, "T21" );
    nameStore.addTransitionName(T22, "T22" );
    nameStore.addTransitionName(T23, "T23" );
    nameStore.addTransitionName(T24, "T24" );
    nameStore.addTransitionName(T25, "T25" );
    nameStore.addTransitionName(T26, "T26" );
    nameStore.addTransitionName(T27, "T27" );
    nameStore.addTransitionName(T28, "T28" );
    nameStore.addTransitionName(T29, "T29" );
    nameStore.addTransitionName(T30, "T30" );
    nameStore.addTransitionName(T31, "T31" );
    nameStore.addTransitionName(T32, "T32" );
    nameStore.addTransitionName(T33_d1, "T33_d1" );
    nameStore.addTransitionName(T34, "T34" );
    nameStore.addTransitionName(T35, "T35" );
    nameStore.addTransitionName(T36, "T36" );
    nameStore.addTransitionName(T37, "T37" );
    nameStore.addTransitionName(T38, "T38" );
    nameStore.addTransitionName(T39, "T39" );
    nameStore.addTransitionName(T40, "T40" );
    nameStore.addTransitionName(T41, "T41" );
    nameStore.addTransitionName(T42, "T42" );
    nameStore.addTransitionName(T43, "T43" );
    nameStore.addTransitionName(T44, "T44" );
    nameStore.addTransitionName(T45, "T45" );
    nameStore.addTransitionName(T46, "T46" );
    nameStore.addTransitionName(T47, "T47" );
    nameStore.addTransitionName(T48, "T48" );
    nameStore.addTransitionName(T49, "T49" );
    nameStore.addTransitionName(T50, "T50" );
    nameStore.addTransitionName(T51, "T51" );
    nameStore.addTransitionName(T52, "T52" );
    nameStore.addTransitionName(T53, "T53" );
    nameStore.addTransitionName(T54, "T54" );
    nameStore.addTransitionName(T55, "T55" );
    nameStore.addTransitionName(T56, "T56" );
    nameStore.addTransitionName(T57, "T57" );
    nameStore.addTransitionName(T58, "T58" );
    nameStore.addTransitionName(T59, "T59" );
    nameStore.addTransitionName(T60, "T60" );
    nameStore.addTransitionName(T61, "T61" );
    nameStore.addTransitionName(T62, "T62" );
    nameStore.addTransitionName(T63, "T63" );
    nameStore.addTransitionName(T64, "T64" );
    nameStore.addTransitionName(T65, "T65" );
    nameStore.addTransitionName(T66, "T66" );
    nameStore.addTransitionName(T67, "T67" );
    nameStore.addTransitionName(T68_d1, "T68_d1" );
    nameStore.addTransitionName(T69, "T69" );
    nameStore.addTransitionName(T70, "T70" );
    nameStore.addTransitionName(T71, "T71" );
    nameStore.addTransitionName(T72, "T72" );
    nameStore.addTransitionName(T73, "T73" );
    nameStore.addTransitionName(T74_d1, "T74_d1" );
    nameStore.addTransitionName(T75, "T75" );
    nameStore.addTransitionName(T76, "T76" );
    nameStore.addTransitionName(T77, "T77" );
    nameStore.addTransitionName(T78, "T78" );
    nameStore.addTransitionName(T79, "T79" );
    nameStore.addTransitionName(T80, "T80" );
    nameStore.addTransitionName(T81, "T81" );
    nameStore.addTransitionName(T82, "T82" );
    nameStore.addTransitionName(T83, "T83" );
    nameStore.addTransitionName(T84, "T84" );
    nameStore.addTransitionName(T85, "T85" );
    nameStore.addTransitionName(T86, "T86" );
    nameStore.addTransitionName(T87, "T87" );
    nameStore.addTransitionName(T88, "T88" );
    nameStore.addTransitionName(T89, "T89" );
    nameStore.addTransitionName(T90, "T90" );
    nameStore.addTransitionName(T91, "T91" );
    nameStore.addTransitionName(T92, "T92" );
    nameStore.addTransitionName(T93, "T93" );
    nameStore.addTransitionName(T94, "T94" );
    nameStore.addTransitionName(T95, "T95" );
    nameStore.addTransitionName(T96, "T96" );
    nameStore.addTransitionName(T97, "T97" );
    nameStore.addTransitionName(T98, "T98" );
    nameStore.addTransitionName(T99, "T99" );
    nameStore.addTransitionName(T100, "T100" );
    nameStore.addTransitionName(T101, "T101" );
    nameStore.addTransitionName(T102, "T102" );
    nameStore.addTransitionName(T103, "T103" );
    nameStore.addTransitionName(T104, "T104" );
    nameStore.addTransitionName(T105, "T105" );
    nameStore.addTransitionName(T106, "T106" );
    nameStore.addTransitionName(T107, "T107" );
    nameStore.addTransitionName(T108, "T108" );
    nameStore.addTransitionName(T109, "T109" );
    nameStore.addTransitionName(T110, "T110" );
    nameStore.addTransitionName(T111, "T111" );
    nameStore.addTransitionName(T112, "T112" );
    nameStore.addTransitionName(T113, "T113" );
    nameStore.addTransitionName(T114, "T114" );
    nameStore.addTransitionName(T115, "T115" );
    nameStore.addTransitionName(T116, "T116" );
    nameStore.addTransitionName(T117, "T117" );
    nameStore.addTransitionName(T118, "T118" );
    nameStore.addTransitionName(T119, "T119" );
    nameStore.addTransitionName(T120, "T120" );
    nameStore.addTransitionName(T121, "T121" );
    nameStore.addTransitionName(T122, "T122" );
    nameStore.addTransitionName(T123, "T123" );
    nameStore.addTransitionName(T124, "T124" );
    nameStore.addTransitionName(T125, "T125" );
    nameStore.addTransitionName(T126, "T126" );
    nameStore.addTransitionName(T131, "T131" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P92, T0);
    net.addArcFromPlaceToTransition(P143, T0);
    net.addArcFromPlaceToTransition(P131, T1);
    net.addArcFromPlaceToTransition(P30, T2);
    net.addArcFromPlaceToTransition(P56, T3);
    net.addArcFromPlaceToTransition(P96, T4_d1);
    net.addArcFromPlaceToTransition(P96, T5);
    net.addArcFromPlaceToTransition(P119, T5);
    net.addArcFromPlaceToTransition(P107, T6);
    net.addArcFromPlaceToTransition(P159, T7);
    net.addArcFromPlaceToTransition(P106, T8);
    net.addArcFromPlaceToTransition(P104, T8);
    net.addArcFromPlaceToTransition(P75, T9);
    net.addArcFromPlaceToTransition(P120, T10);
    net.addArcFromPlaceToTransition(P118, T11);
    net.addArcFromPlaceToTransition(P142, T12);
    net.addArcFromPlaceToTransition(P92, T12);
    net.addArcFromPlaceToTransition(P74, T13);
    net.addArcFromPlaceToTransition(P92, T14);
    net.addArcFromPlaceToTransition(P141, T14);
    net.addArcFromPlaceToTransition(P94, T15);
    net.addArcFromPlaceToTransition(P122, T15);
    net.addArcFromPlaceToTransition(P95, T16);
    net.addArcFromPlaceToTransition(P27, T17);
    net.addArcFromPlaceToTransition(P17, T17);
    net.addArcFromPlaceToTransition(P5, T18);
    net.addArcFromPlaceToTransition(P154, T19);
    net.addArcFromPlaceToTransition(P82, T20);
    net.addArcFromPlaceToTransition(P79, T21);
    net.addArcFromPlaceToTransition(P52, T22);
    net.addArcFromPlaceToTransition(P50, T22);
    net.addArcFromPlaceToTransition(P77, T23);
    net.addArcFromPlaceToTransition(P76, T24);
    net.addArcFromPlaceToTransition(P55, T25);
    net.addArcFromPlaceToTransition(P60, T26);
    net.addArcFromPlaceToTransition(P114, T27);
    net.addArcFromPlaceToTransition(P127, T27);
    net.addArcFromPlaceToTransition(P125, T28);
    net.addArcFromPlaceToTransition(P114, T28);
    net.addArcFromPlaceToTransition(P114, T29);
    net.addArcFromPlaceToTransition(P117, T29);
    net.addArcFromPlaceToTransition(P39, T30);
    net.addArcFromPlaceToTransition(P7, T30);
    net.addArcFromPlaceToTransition(P138, T31);
    net.addArcFromPlaceToTransition(P124, T32);
    net.addArcFromPlaceToTransition(P163, T32);
    net.addArcFromPlaceToTransition(P124, T33_d1);
    net.addArcFromPlaceToTransition(P19, T34);
    net.addArcFromPlaceToTransition(P126, T35);
    net.addArcFromPlaceToTransition(P136, T36);
    net.addArcFromPlaceToTransition(P128, T37);
    net.addArcFromPlaceToTransition(P158, T37);
    net.addArcFromPlaceToTransition(iP149, T38);
    net.addArcFromPlaceToTransition(P115, T39);
    net.addArcFromPlaceToTransition(P130, T40);
    net.addArcFromPlaceToTransition(P132, T40);
    net.addArcFromPlaceToTransition(iP155, T41);
    net.addArcFromPlaceToTransition(P16, T42);
    net.addArcFromPlaceToTransition(iP153, T43);
    net.addArcFromPlaceToTransition(iP160, T44);
    net.addArcFromPlaceToTransition(iP157, T45);
    net.addArcFromPlaceToTransition(iP162, T46);
    net.addArcFromPlaceToTransition(iP161, T47);
    net.addArcFromPlaceToTransition(iP1, T48);
    net.addArcFromPlaceToTransition(P129, T49);
    net.addArcFromPlaceToTransition(iP0, T50);
    net.addArcFromPlaceToTransition(iP2, T51);
    net.addArcFromPlaceToTransition(P69, T52);
    net.addArcFromPlaceToTransition(P109, T52);
    net.addArcFromPlaceToTransition(P63, T53);
    net.addArcFromPlaceToTransition(P68, T53);
    net.addArcFromPlaceToTransition(P6, T54);
    net.addArcFromPlaceToTransition(P29, T55);
    net.addArcFromPlaceToTransition(P48, T56);
    net.addArcFromPlaceToTransition(P134, T57);
    net.addArcFromPlaceToTransition(iP66, T58);
    net.addArcFromPlaceToTransition(P44, T59);
    net.addArcFromPlaceToTransition(P151, T60);
    net.addArcFromPlaceToTransition(P15, T60);
    net.addArcFromPlaceToTransition(iP67, T61);
    net.addArcFromPlaceToTransition(P9, T62);
    net.addArcFromPlaceToTransition(P151, T62);
    net.addArcFromPlaceToTransition(P151, T63);
    net.addArcFromPlaceToTransition(P10, T63);
    net.addArcFromPlaceToTransition(P135, T64);
    net.addArcFromPlaceToTransition(P145, T64);
    net.addArcFromPlaceToTransition(P146, T65);
    net.addArcFromPlaceToTransition(P147, T66);
    net.addArcFromPlaceToTransition(P133, T66);
    net.addArcFromPlaceToTransition(P148, T67);
    net.addArcFromPlaceToTransition(P24, T67);
    net.addArcFromPlaceToTransition(P148, T68_d1);
    net.addArcFromPlaceToTransition(P152, T69);
    net.addArcFromPlaceToTransition(P156, T69);
    net.addArcFromPlaceToTransition(P150, T70);
    net.addArcFromPlaceToTransition(P137, T71);
    net.addArcFromPlaceToTransition(P139, T72);
    net.addArcFromPlaceToTransition(P123, T72);
    net.addArcFromPlaceToTransition(P140, T73);
    net.addArcFromPlaceToTransition(P121, T73);
    net.addArcFromPlaceToTransition(P140, T74_d1);
    net.addArcFromPlaceToTransition(P86, T75);
    net.addArcFromPlaceToTransition(P85, T75);
    net.addArcFromPlaceToTransition(P83, T76);
    net.addArcFromPlaceToTransition(P81, T76);
    net.addArcFromPlaceToTransition(P91, T77);
    net.addArcFromPlaceToTransition(P90, T77);
    net.addArcFromPlaceToTransition(P88, T78);
    net.addArcFromPlaceToTransition(P87, T78);
    net.addArcFromPlaceToTransition(P25, T79);
    net.addArcFromPlaceToTransition(P21, T80);
    net.addArcFromPlaceToTransition(P22, T81);
    net.addArcFromPlaceToTransition(P8, T82);
    net.addArcFromPlaceToTransition(P23, T83);
    net.addArcFromPlaceToTransition(P45, T84);
    net.addArcFromPlaceToTransition(P41, T85);
    net.addArcFromPlaceToTransition(P57, T86);
    net.addArcFromPlaceToTransition(P144, T87);
    net.addArcFromPlaceToTransition(P64, T88);
    net.addArcFromPlaceToTransition(P105, T89);
    net.addArcFromPlaceToTransition(P103, T90);
    net.addArcFromPlaceToTransition(P111, T91);
    net.addArcFromPlaceToTransition(P35, T91);
    net.addArcFromPlaceToTransition(P98, T92);
    net.addArcFromPlaceToTransition(P42, T93);
    net.addArcFromPlaceToTransition(P116, T93);
    net.addArcFromPlaceToTransition(P37, T94);
    net.addArcFromPlaceToTransition(P36, T95);
    net.addArcFromPlaceToTransition(P38, T95);
    net.addArcFromPlaceToTransition(P97, T96);
    net.addArcFromPlaceToTransition(P46, T97);
    net.addArcFromPlaceToTransition(P49, T97);
    net.addArcFromPlaceToTransition(P47, T98);
    net.addArcFromPlaceToTransition(P43, T98);
    net.addArcFromPlaceToTransition(P116, T99);
    net.addArcFromPlaceToTransition(P51, T99);
    net.addArcFromPlaceToTransition(P35, T100);
    net.addArcFromPlaceToTransition(P40, T100);
    net.addArcFromPlaceToTransition(P3, T101);
    net.addArcFromPlaceToTransition(P61, T102);
    net.addArcFromPlaceToTransition(P59, T102);
    net.addArcFromPlaceToTransition(P4, T103);
    net.addArcFromPlaceToTransition(P89, T104);
    net.addArcFromPlaceToTransition(P26, T105);
    net.addArcFromPlaceToTransition(P108, T105);
    net.addArcFromPlaceToTransition(P108, T106);
    net.addArcFromPlaceToTransition(P58, T106);
    net.addArcFromPlaceToTransition(P28, T107);
    net.addArcFromPlaceToTransition(P54, T107);
    net.addArcFromPlaceToTransition(P31, T108);
    net.addArcFromPlaceToTransition(P62, T109);
    net.addArcFromPlaceToTransition(P33, T110);
    net.addArcFromPlaceToTransition(P32, T111);
    net.addArcFromPlaceToTransition(P34, T112);
    net.addArcFromPlaceToTransition(P65, T113);
    net.addArcFromPlaceToTransition(P112, T113);
    net.addArcFromPlaceToTransition(P65, T114);
    net.addArcFromPlaceToTransition(P110, T114);
    net.addArcFromPlaceToTransition(P14, T115);
    net.addArcFromPlaceToTransition(P113, T116);
    net.addArcFromPlaceToTransition(P65, T116);
    net.addArcFromPlaceToTransition(P11, T117);
    net.addArcFromPlaceToTransition(P13, T118);
    net.addArcFromPlaceToTransition(P18, T119);
    net.addArcFromPlaceToTransition(P20, T120);
    net.addArcFromPlaceToTransition(P71, T121);
    net.addArcFromPlaceToTransition(P78, T122);
    net.addArcFromPlaceToTransition(P93, T122);
    net.addArcFromPlaceToTransition(P80, T123);
    net.addArcFromPlaceToTransition(P84, T123);
    net.addArcFromPlaceToTransition(P93, T124);
    net.addArcFromPlaceToTransition(P72, T124);
    net.addArcFromPlaceToTransition(P73, T125);
    net.addArcFromPlaceToTransition(P70, T125);
    net.addArcFromPlaceToTransition(P12, T126);
    net.addArcFromPlaceToTransition(P102, oT127);
    net.addArcFromPlaceToTransition(P100, oT128);
    net.addArcFromPlaceToTransition(P101, oT129);
    net.addArcFromPlaceToTransition(P99, oT130);
    net.addArcFromPlaceToTransition(P53, T131);
    net.addArcFromTransitionToPlace(T0, P92);
    net.addArcFromTransitionToPlace(T1, P117);
    net.addArcFromTransitionToPlace(T2, P75);
    net.addArcFromTransitionToPlace(T3, P74);
    net.addArcFromTransitionToPlace(T4_d1, P95);
    net.addArcFromTransitionToPlace(T5, P94);
    net.addArcFromTransitionToPlace(T6, P105);
    net.addArcFromTransitionToPlace(T6, P27);
    net.addArcFromTransitionToPlace(T7, P109);
    net.addArcFromTransitionToPlace(T8, P107);
    net.addArcFromTransitionToPlace(T8, P104);
    net.addArcFromTransitionToPlace(T9, P85);
    net.addArcFromTransitionToPlace(T10, P90);
    net.addArcFromTransitionToPlace(T11, P83);
    net.addArcFromTransitionToPlace(T12, P92);
    net.addArcFromTransitionToPlace(T13, P88);
    net.addArcFromTransitionToPlace(T14, P92);
    net.addArcFromTransitionToPlace(T14, P89);
    net.addArcFromTransitionToPlace(T15, P95);
    net.addArcFromTransitionToPlace(T16, P96);
    net.addArcFromTransitionToPlace(T16, P106);
    net.addArcFromTransitionToPlace(T17, P29);
    net.addArcFromTransitionToPlace(T18, P86);
    net.addArcFromTransitionToPlace(T19, P141);
    net.addArcFromTransitionToPlace(T20, P60);
    net.addArcFromTransitionToPlace(T21, P55);
    net.addArcFromTransitionToPlace(T22, P48);
    net.addArcFromTransitionToPlace(T23, P50);
    net.addArcFromTransitionToPlace(T23, P49);
    net.addArcFromTransitionToPlace(T24, P52);
    net.addArcFromTransitionToPlace(T24, P51);
    net.addArcFromTransitionToPlace(T25, P87);
    net.addArcFromTransitionToPlace(T26, P91);
    net.addArcFromTransitionToPlace(T27, P114);
    net.addArcFromTransitionToPlace(T28, P114);
    net.addArcFromTransitionToPlace(T29, P114);
    net.addArcFromTransitionToPlace(T29, P103);
    net.addArcFromTransitionToPlace(T30, P37);
    net.addArcFromTransitionToPlace(T31, P122);
    net.addArcFromTransitionToPlace(T32, P128);
    net.addArcFromTransitionToPlace(T33_d1, P126);
    net.addArcFromTransitionToPlace(T34, P113);
    net.addArcFromTransitionToPlace(T35, P124);
    net.addArcFromTransitionToPlace(T35, P130);
    net.addArcFromTransitionToPlace(T36, P119);
    net.addArcFromTransitionToPlace(T37, P126);
    net.addArcFromTransitionToPlace(T38, P138);
    net.addArcFromTransitionToPlace(T38, P11);
    net.addArcFromTransitionToPlace(T39, P112);
    net.addArcFromTransitionToPlace(T39, P77);
    net.addArcFromTransitionToPlace(T40, P129);
    net.addArcFromTransitionToPlace(T40, P132);
    net.addArcFromTransitionToPlace(T41, P136);
    net.addArcFromTransitionToPlace(T41, P13);
    net.addArcFromTransitionToPlace(T42, P76);
    net.addArcFromTransitionToPlace(T43, P23);
    net.addArcFromTransitionToPlace(T43, P34);
    net.addArcFromTransitionToPlace(T44, P25);
    net.addArcFromTransitionToPlace(T44, P33);
    net.addArcFromTransitionToPlace(T45, P16);
    net.addArcFromTransitionToPlace(T46, P22);
    net.addArcFromTransitionToPlace(T47, P20);
    net.addArcFromTransitionToPlace(T48, P31);
    net.addArcFromTransitionToPlace(T49, P131);
    net.addArcFromTransitionToPlace(T49, P63);
    net.addArcFromTransitionToPlace(T50, P19);
    net.addArcFromTransitionToPlace(T51, P21);
    net.addArcFromTransitionToPlace(T52, P53);
    net.addArcFromTransitionToPlace(T53, P57);
    net.addArcFromTransitionToPlace(T54, P81);
    net.addArcFromTransitionToPlace(T55, P115);
    net.addArcFromTransitionToPlace(T56, P118);
    net.addArcFromTransitionToPlace(T57, P144);
    net.addArcFromTransitionToPlace(T57, P69);
    net.addArcFromTransitionToPlace(T58, P18);
    net.addArcFromTransitionToPlace(T59, P120);
    net.addArcFromTransitionToPlace(T60, P151);
    net.addArcFromTransitionToPlace(T60, P159);
    net.addArcFromTransitionToPlace(T61, P32);
    net.addArcFromTransitionToPlace(T62, P151);
    net.addArcFromTransitionToPlace(T63, P151);
    net.addArcFromTransitionToPlace(T64, P134);
    net.addArcFromTransitionToPlace(T64, P145);
    net.addArcFromTransitionToPlace(T65, P148);
    net.addArcFromTransitionToPlace(T65, P152);
    net.addArcFromTransitionToPlace(T66, P146);
    net.addArcFromTransitionToPlace(T67, P147);
    net.addArcFromTransitionToPlace(T68_d1, P146);
    net.addArcFromTransitionToPlace(T69, P150);
    net.addArcFromTransitionToPlace(T69, P156);
    net.addArcFromTransitionToPlace(T70, P154);
    net.addArcFromTransitionToPlace(T70, P39);
    net.addArcFromTransitionToPlace(T71, P140);
    net.addArcFromTransitionToPlace(T71, P135);
    net.addArcFromTransitionToPlace(T72, P137);
    net.addArcFromTransitionToPlace(T73, P139);
    net.addArcFromTransitionToPlace(T74_d1, P137);
    net.addArcFromTransitionToPlace(T75, P101);
    net.addArcFromTransitionToPlace(T76, P99);
    net.addArcFromTransitionToPlace(T77, P102);
    net.addArcFromTransitionToPlace(T78, P100);
    net.addArcFromTransitionToPlace(T79, P121);
    net.addArcFromTransitionToPlace(T80, P9);
    net.addArcFromTransitionToPlace(T81, P98);
    net.addArcFromTransitionToPlace(T82, P10);
    net.addArcFromTransitionToPlace(T82, P97);
    net.addArcFromTransitionToPlace(T83, P123);
    net.addArcFromTransitionToPlace(T84, P6);
    net.addArcFromTransitionToPlace(T85, P5);
    net.addArcFromTransitionToPlace(T86, P62);
    net.addArcFromTransitionToPlace(T87, P15);
    net.addArcFromTransitionToPlace(T88, P17);
    net.addArcFromTransitionToPlace(T89, P110);
    net.addArcFromTransitionToPlace(T90, P68);
    net.addArcFromTransitionToPlace(T91, P35);
    net.addArcFromTransitionToPlace(T91, P111);
    net.addArcFromTransitionToPlace(T92, P36);
    net.addArcFromTransitionToPlace(T92, P40);
    net.addArcFromTransitionToPlace(T93, P116);
    net.addArcFromTransitionToPlace(T93, P42);
    net.addArcFromTransitionToPlace(T94, P71);
    net.addArcFromTransitionToPlace(T95, P41);
    net.addArcFromTransitionToPlace(T96, P38);
    net.addArcFromTransitionToPlace(T96, P43);
    net.addArcFromTransitionToPlace(T97, P44);
    net.addArcFromTransitionToPlace(T98, P45);
    net.addArcFromTransitionToPlace(T99, P46);
    net.addArcFromTransitionToPlace(T100, P47);
    net.addArcFromTransitionToPlace(T101, P59);
    net.addArcFromTransitionToPlace(T101, P54);
    net.addArcFromTransitionToPlace(T102, P56);
    net.addArcFromTransitionToPlace(T103, P61);
    net.addArcFromTransitionToPlace(T103, P58);
    net.addArcFromTransitionToPlace(T104, P7);
    net.addArcFromTransitionToPlace(T105, P108);
    net.addArcFromTransitionToPlace(T105, P26);
    net.addArcFromTransitionToPlace(T106, P28);
    net.addArcFromTransitionToPlace(T107, P30);
    net.addArcFromTransitionToPlace(T108, P14);
    net.addArcFromTransitionToPlace(T109, P127);
    net.addArcFromTransitionToPlace(T109, P12);
    net.addArcFromTransitionToPlace(T110, P163);
    net.addArcFromTransitionToPlace(T111, P125);
    net.addArcFromTransitionToPlace(T112, P158);
    net.addArcFromTransitionToPlace(T113, P65);
    net.addArcFromTransitionToPlace(T114, P65);
    net.addArcFromTransitionToPlace(T114, P64);
    net.addArcFromTransitionToPlace(T115, P73);
    net.addArcFromTransitionToPlace(T115, P72);
    net.addArcFromTransitionToPlace(T116, P65);
    net.addArcFromTransitionToPlace(T117, P133);
    net.addArcFromTransitionToPlace(T118, P24);
    net.addArcFromTransitionToPlace(T119, P142);
    net.addArcFromTransitionToPlace(T120, P4);
    net.addArcFromTransitionToPlace(T121, P143);
    net.addArcFromTransitionToPlace(T121, P3);
    net.addArcFromTransitionToPlace(T122, P93);
    net.addArcFromTransitionToPlace(T122, P78);
    net.addArcFromTransitionToPlace(T123, P79);
    net.addArcFromTransitionToPlace(T124, P80);
    net.addArcFromTransitionToPlace(T125, P82);
    net.addArcFromTransitionToPlace(T126, P70);
    net.addArcFromTransitionToPlace(T126, P84);
    net.addArcFromTransitionToPlace(T131, P8);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P26, new UnifiedToken(1.0));
    net.setInitialMarkingForPlace(P42, new UnifiedToken(1.0));
    net.setInitialMarkingForPlace(P65, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P78, new UnifiedToken(1.0));
    net.setInitialMarkingForPlace(P92, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P94, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P104, new UnifiedToken(20.0));
    net.setInitialMarkingForPlace(P111, new UnifiedToken(1.0));
    net.setInitialMarkingForPlace(P114, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P128, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P132, new UnifiedToken(20.0));
    net.setInitialMarkingForPlace(P139, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P145, new UnifiedToken(20.0));
    net.setInitialMarkingForPlace(P147, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P151, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P156, new UnifiedToken(20.0));
  }

}