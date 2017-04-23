import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class FullIntersectionUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP8, iP7, iP9, iP10, iP11, iP0, iP2, iP1, iP4, iP3, iP6, iP5 ;
  /*ordinary places */
  public int _P3, _P4, _P5, _P6, _P7, _P8, _P9, _P10, _P11, _P12, _P13, _P14, _P15, _P16, _P17, _P18, _P19, _P20, _P21, _P22, _P23, _P24, _P25, _P26, _P27, _P28, _P29, _P30, _P31, _P32, _P33, _P34, _P35, _P36, _P37, _P38, _P39, _P40, _P41, _P42, _P43, _P44, _P45, _P46, _P47, _P48, _P49, _P50, _P51, _P52, _P53, _P54, _P55, _P56, _P57, _P58, _P59, _P60, _P61, _P62, _P63, _P64, _P65, _P66, _P67, _P68, _P69, _P70, _P71, _P74, _P75, _P76, _P77, _P78, _P79, _P80, _P81, _P82, _P83, _P84, _P85, _P86, _P87, P21, _P89, P20, _P91, P23, P22, P25, P24, _P96, P27, P26, _P99, _P100, _P101, _P102, _P103, _P104, _P105, P30, P32, P31, P33, _P110, _P111, _P112, _P113, _P114, _P115, _P116, _P117, _P118, _P119, _P120, _P121, P41, P40, P43, P42, _P126, _P127, _P128, _P129, _P130, _P131, _P132, _P133, _P134, _P135, _P136, _P137, _P138, _P139, _P140, _P141, _P142, _P143, _P144, _P145, _P146, _P147, _P148, _P149, _P150, _P151, _P152, _P153, _P154, _P155, _P156, _P157, _P158, _P159, _P160, _P162, _P163, _P164, _P166, _P168, _P170, _P171, _P175 ;
  /* output transitions */
  public int oT6, oT5, oT8, oT7, oT2, oT1, oT4, oT3 ;
  /* table for out transitions */
  String oT6_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT5_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT8_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT7_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT4_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int qThree_iLaneOne_T2, qFour_calcOne_oT9, qThree_splitOne_oT6, qThree_splitOne_oT5, qOne_calcOne_T2, qOne_calcOne_T3, qOne_calcOne_T8, qTwo_iLaneOne_oT3, qOne_calcOne_T7, qOne_oT3, qThree_oT2, qOne_oT2, qThree_oT3, qOne_oT1, qThree_iLaneOne_T1, qThree_oT1, qThree_iLaneOne_T0, qOne_calcOne_T0, qOne_calcOne_T1, qOne_calcOne_T10, qTwo_oT1, qThree_calcOne_oT9, qFour_splitOne_oT5, qFour_splitOne_oT6, qOne_splitOne_T2, qOne_splitOne_T1, qOne_splitOne_T0, qFour_oT3, qFour_oT2, qFour_oT1, qFour_iLaneOne_T2, qFour_iLaneOne_T1, qFour_iLaneOne_T0, qThree_calcOne_T10, qOne_T0, qFour_calcOne_T3, qFour_calcOne_T2, qOne_T2, qFour_calcOne_T1, qOne_T1, qFour_calcOne_T0, T0, qOne_T4, qFour_calcOne_T7, T1, qOne_T3, T2, T3, T4, T5, T6, T7, qFour_calcOne_T8, T8, T9, qTwo_calcOne_T10, qFour_calcOne_T10, qOne_calcOne_oT14, qTwo_oT2, qOne_calcOne_oT13, qTwo_oT3, qOne_splitOne_oT5, qTwo_calcOne_T8, T10, qOne_splitOne_oT6, qTwo_iLaneOne_T0, T11, qTwo_iLaneOne_T1, qTwo_iLaneOne_T2, qTwo_calcOne_T7, qThree_calcOne_T1, qThree_calcOne_T0, qThree_calcOne_T3, qThree_calcOne_T2, qThree_calcOne_T7, qThree_calcOne_T8, qTwo_calcOne_T1, qTwo_calcOne_T0, qTwo_calcOne_T3, qTwo_calcOne_T2, T21, T20, T23, T22, qTwo_T1, qTwo_T2, qTwo_T3, qTwo_T4, qTwo_T0, qTwo_splitOne_oT6, qTwo_splitOne_oT5, qFour_calcOne_oT14, qFour_calcOne_oT13, qTwo_calcOne_oT9, qOne_iLaneOne_oT3, qOne_calcOne_oT9, qFour_iLaneOne_oT3, qTwo_splitOne_T7, qTwo_splitOne_T0, qOne_splitOne_T7, qThree_calcOne_oT13, qTwo_splitOne_T2, qTwo_splitOne_T1, qOne_splitOne_T4, qTwo_splitOne_T4, qOne_splitOne_T3, qThree_calcOne_oT14, qTwo_splitOne_T3, qThree_splitOne_T1, qThree_splitOne_T2, qThree_splitOne_T0, qThree_iLaneOne_oT3, qThree_splitOne_T7, qThree_splitOne_T3, qThree_splitOne_T4, qFour_T3, qFour_T4, qFour_T1, qFour_T2, qFour_T0, qOne_iLaneOne_T2, qOne_iLaneOne_T0, qFour_splitOne_T0, qOne_iLaneOne_T1, qThree_T0, qThree_T1, qThree_T2, qThree_T3, qThree_T4, qTwo_calcOne_oT14, qFour_splitOne_T7, qFour_splitOne_T4, qFour_splitOne_T3, qFour_splitOne_T2, qFour_splitOne_T1, qTwo_calcOne_oT13 ;
  /* table for delay transitions */
  String qThree_iLaneOne_T2_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qFour_calcOne_oT9_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_splitOne_oT6_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_splitOne_oT5_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_calcOne_T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_calcOne_T3_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qOne_calcOne_T8_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qTwo_iLaneOne_oT3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_calcOne_T7_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String qOne_oT3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_oT2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_oT2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_oT3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_iLaneOne_T1_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qThree_oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_iLaneOne_T0_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String qOne_calcOne_T0_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qOne_calcOne_T1_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qOne_calcOne_T10_tableStr = "{"+
  	"[<-2,-2><-2,-2><-2, 0><-2,-2><-2,-2><FF,-2>]"+//
  	"[<-2,-1><-1,-1><-1,-1><-1,-1><-1,-1><FF,-1>]"+//
  	"[<-2, 0><-1, 0>< 0, 0>< 0, 0>< 0, 0><FF, 0>]"+//
  	"[<-2, 1><-1, 1>< 0, 1>< 1, 1>< 1, 1><FF, 1>]"+//
  	"[<-2, 2><-1, 2>< 0, 2>< 1, 2>< 2, 2><FF, 2>]"+//
  	"[<FF, 0><FF, 0><FF, 0><FF, 0><FF, 0><FF,FF>]"+//
  "}";
  String qTwo_oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_calcOne_oT9_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_splitOne_oT5_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_splitOne_oT6_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_splitOne_T2_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qOne_splitOne_T1_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qOne_splitOne_T0_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qFour_oT3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_oT2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_iLaneOne_T2_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qFour_iLaneOne_T1_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qFour_iLaneOne_T0_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String qThree_calcOne_T10_tableStr = "{"+
  	"[<-2,-2><-2,-2><-2, 0><-2,-2><-2,-2><FF,-2>]"+//
  	"[<-2,-1><-1,-1><-1,-1><-1,-1><-1,-1><FF,-1>]"+//
  	"[<-2, 0><-1, 0>< 0, 0>< 0, 0>< 0, 0><FF, 0>]"+//
  	"[<-2, 1><-1, 1>< 0, 1>< 1, 1>< 1, 1><FF, 1>]"+//
  	"[<-2, 2><-1, 2>< 0, 2>< 1, 2>< 2, 2><FF, 2>]"+//
  	"[<FF, 0><FF, 0><FF, 0><FF, 0><FF, 0><FF,FF>]"+//
  "}";
  String qOne_T0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_calcOne_T3_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qFour_calcOne_T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_calcOne_T1_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qOne_T1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_calcOne_T0_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T0_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qOne_T4_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qFour_calcOne_T7_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T1_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qOne_T3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T2_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T3_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T4_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T5_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T6_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T7_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_calcOne_T8_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T8_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T9_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_calcOne_T10_tableStr = "{"+
  	"[<-2,-2><-2,-2><-2, 0><-2,-2><-2,-2><FF,-2>]"+//
  	"[<-2,-1><-1,-1><-1,-1><-1,-1><-1,-1><FF,-1>]"+//
  	"[<-2, 0><-1, 0>< 0, 0>< 0, 0>< 0, 0><FF, 0>]"+//
  	"[<-2, 1><-1, 1>< 0, 1>< 1, 1>< 1, 1><FF, 1>]"+//
  	"[<-2, 2><-1, 2>< 0, 2>< 1, 2>< 2, 2><FF, 2>]"+//
  	"[<FF, 0><FF, 0><FF, 0><FF, 0><FF, 0><FF,FF>]"+//
  "}";
  String qFour_calcOne_T10_tableStr = "{"+
  	"[<-2,-2><-2,-2><-2, 0><-2,-2><-2,-2><FF,-2>]"+//
  	"[<-2,-1><-1,-1><-1,-1><-1,-1><-1,-1><FF,-1>]"+//
  	"[<-2, 0><-1, 0>< 0, 0>< 0, 0>< 0, 0><FF, 0>]"+//
  	"[<-2, 1><-1, 1>< 0, 1>< 1, 1>< 1, 1><FF, 1>]"+//
  	"[<-2, 2><-1, 2>< 0, 2>< 1, 2>< 2, 2><FF, 2>]"+//
  	"[<FF, 0><FF, 0><FF, 0><FF, 0><FF, 0><FF,FF>]"+//
  "}";
  String qOne_calcOne_oT14_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_oT2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_calcOne_oT13_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_oT3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_splitOne_oT5_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_calcOne_T8_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T10_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_splitOne_oT6_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_iLaneOne_T0_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T11_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_iLaneOne_T1_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qTwo_iLaneOne_T2_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qTwo_calcOne_T7_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String qThree_calcOne_T1_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qThree_calcOne_T0_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qThree_calcOne_T3_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qThree_calcOne_T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_calcOne_T7_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String qThree_calcOne_T8_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qTwo_calcOne_T1_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qTwo_calcOne_T0_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qTwo_calcOne_T3_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qTwo_calcOne_T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T21_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><-2>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><-1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 0>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 2>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  "}";
  String T20_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><-2>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><-1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 0>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 2>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  "}";
  String T23_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><-2>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><-1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 0>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 2>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  "}";
  String T22_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><-2>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><-1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 0>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 2>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  "}";
  String qTwo_T1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_T3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_T4_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qTwo_T0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_splitOne_oT6_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_splitOne_oT5_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_calcOne_oT14_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_calcOne_oT13_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_calcOne_oT9_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_iLaneOne_oT3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_calcOne_oT9_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_iLaneOne_oT3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_splitOne_T7_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String qTwo_splitOne_T0_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qOne_splitOne_T7_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String qThree_calcOne_oT13_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_splitOne_T2_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qTwo_splitOne_T1_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qOne_splitOne_T4_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qTwo_splitOne_T4_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qOne_splitOne_T3_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qThree_calcOne_oT14_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qTwo_splitOne_T3_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qThree_splitOne_T1_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qThree_splitOne_T2_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qThree_splitOne_T0_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qThree_iLaneOne_oT3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_splitOne_T7_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String qThree_splitOne_T3_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qThree_splitOne_T4_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qFour_T3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_T4_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qFour_T1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_T0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qOne_iLaneOne_T2_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qOne_iLaneOne_T0_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String qFour_splitOne_T0_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qOne_iLaneOne_T1_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qThree_T0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_T1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_T3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qThree_T4_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qTwo_calcOne_oT14_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String qFour_splitOne_T7_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String qFour_splitOne_T4_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qFour_splitOne_T3_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qFour_splitOne_T2_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String qFour_splitOne_T1_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String qTwo_calcOne_oT13_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public FullIntersectionUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP8 = net.addInputPlace(50.0);
    iP7 = net.addInputPlace(1.0);
    iP9 = net.addInputPlace(50.0);
    iP10 = net.addInputPlace(50.0);
    iP11 = net.addInputPlace(50.0);
    iP0 = net.addInputPlace(0.0);
    iP2 = net.addInputPlace(0.0);
    iP1 = net.addInputPlace(0.0);
    iP4 = net.addInputPlace(1.0);
    iP3 = net.addInputPlace(0.0);
    iP6 = net.addInputPlace(1.0);
    iP5 = net.addInputPlace(1.0);
    _P3 = net.addPlace(50.0);
    _P4 = net.addPlace(1.0);
    _P5 = net.addPlace(50.0);
    _P6 = net.addPlace(50.0);
    _P7 = net.addPlace(50.0);
    _P8 = net.addPlace(50.0);
    _P9 = net.addPlace(50.0);
    _P10 = net.addPlace(50.0);
    _P11 = net.addPlace(50.0);
    _P12 = net.addPlace(0.0);
    _P13 = net.addPlace(50.0);
    _P14 = net.addPlace(0.0);
    _P15 = net.addPlace(1.0);
    _P16 = net.addPlace(50.0);
    _P17 = net.addPlace(1.0);
    _P18 = net.addPlace(50.0);
    _P19 = net.addPlace(50.0);
    _P20 = net.addPlace(50.0);
    _P21 = net.addPlace(1.0);
    _P22 = net.addPlace(50.0);
    _P23 = net.addPlace(50.0);
    _P24 = net.addPlace(1.0);
    _P25 = net.addPlace(0.0);
    _P26 = net.addPlace(0.0);
    _P27 = net.addPlace(0.0);
    _P28 = net.addPlace(1.0);
    _P29 = net.addPlace(50.0);
    _P30 = net.addPlace(1.0);
    _P31 = net.addPlace(50.0);
    _P32 = net.addPlace(50.0);
    _P33 = net.addPlace(1.0);
    _P34 = net.addPlace(50.0);
    _P35 = net.addPlace(0.0);
    _P36 = net.addPlace(0.0);
    _P37 = net.addPlace(1.0);
    _P38 = net.addPlace(1.0);
    _P39 = net.addPlace(50.0);
    _P40 = net.addPlace(50.0);
    _P41 = net.addPlace(50.0);
    _P42 = net.addPlace(1.0);
    _P43 = net.addPlace(50.0);
    _P44 = net.addPlace(1.0);
    _P45 = net.addPlace(50.0);
    _P46 = net.addPlace(50.0);
    _P47 = net.addPlace(50.0);
    _P48 = net.addPlace(50.0);
    _P49 = net.addPlace(1.0);
    _P50 = net.addPlace(1.0);
    _P51 = net.addPlace(50.0);
    _P52 = net.addPlace(50.0);
    _P53 = net.addPlace(50.0);
    _P54 = net.addPlace(1.0);
    _P55 = net.addPlace(1.0);
    _P56 = net.addPlace(50.0);
    _P57 = net.addPlace(50.0);
    _P58 = net.addPlace(50.0);
    _P59 = net.addPlace(50.0);
    _P60 = net.addPlace(50.0);
    _P61 = net.addPlace(50.0);
    _P62 = net.addPlace(50.0);
    _P63 = net.addPlace(1.0);
    _P64 = net.addPlace(50.0);
    _P65 = net.addPlace(50.0);
    _P66 = net.addPlace(50.0);
    _P67 = net.addPlace(1.0);
    _P68 = net.addPlace(50.0);
    _P69 = net.addPlace(50.0);
    _P70 = net.addPlace(50.0);
    _P71 = net.addPlace(50.0);
    _P74 = net.addPlace(50.0);
    _P75 = net.addPlace(50.0);
    _P76 = net.addPlace(50.0);
    _P77 = net.addPlace(50.0);
    _P78 = net.addPlace(1.0);
    _P79 = net.addPlace(1.0);
    _P80 = net.addPlace(50.0);
    _P81 = net.addPlace(50.0);
    _P82 = net.addPlace(50.0);
    _P83 = net.addPlace(1.0);
    _P84 = net.addPlace(50.0);
    _P85 = net.addPlace(1.0);
    _P86 = net.addPlace(50.0);
    _P87 = net.addPlace(1.0);
    P21 = net.addPlace(50.0);
    _P89 = net.addPlace(50.0);
    P20 = net.addPlace(50.0);
    _P91 = net.addPlace(50.0);
    P23 = net.addPlace(50.0);
    P22 = net.addPlace(50.0);
    P25 = net.addPlace(50.0);
    P24 = net.addPlace(50.0);
    _P96 = net.addPlace(50.0);
    P27 = net.addPlace(50.0);
    P26 = net.addPlace(50.0);
    _P99 = net.addPlace(50.0);
    _P100 = net.addPlace(1.0);
    _P101 = net.addPlace(0.0);
    _P102 = net.addPlace(0.0);
    _P103 = net.addPlace(0.0);
    _P104 = net.addPlace(50.0);
    _P105 = net.addPlace(1.0);
    P30 = net.addPlace(100.0);
    P32 = net.addPlace(100.0);
    P31 = net.addPlace(100.0);
    P33 = net.addPlace(100.0);
    _P110 = net.addPlace(50.0);
    _P111 = net.addPlace(50.0);
    _P112 = net.addPlace(50.0);
    _P113 = net.addPlace(0.0);
    _P114 = net.addPlace(50.0);
    _P115 = net.addPlace(1.0);
    _P116 = net.addPlace(50.0);
    _P117 = net.addPlace(50.0);
    _P118 = net.addPlace(1.0);
    _P119 = net.addPlace(50.0);
    _P120 = net.addPlace(50.0);
    _P121 = net.addPlace(50.0);
    P41 = net.addPlace(50.0);
    P40 = net.addPlace(50.0);
    P43 = net.addPlace(50.0);
    P42 = net.addPlace(50.0);
    _P126 = net.addPlace(50.0);
    _P127 = net.addPlace(1.0);
    _P128 = net.addPlace(50.0);
    _P129 = net.addPlace(50.0);
    _P130 = net.addPlace(50.0);
    _P131 = net.addPlace(0.0);
    _P132 = net.addPlace(50.0);
    _P133 = net.addPlace(0.0);
    _P134 = net.addPlace(0.0);
    _P135 = net.addPlace(0.0);
    _P136 = net.addPlace(0.0);
    _P137 = net.addPlace(50.0);
    _P138 = net.addPlace(0.0);
    _P139 = net.addPlace(50.0);
    _P140 = net.addPlace(0.0);
    _P141 = net.addPlace(50.0);
    _P142 = net.addPlace(0.0);
    _P143 = net.addPlace(50.0);
    _P144 = net.addPlace(50.0);
    _P145 = net.addPlace(0.0);
    _P146 = net.addPlace(50.0);
    _P147 = net.addPlace(0.0);
    _P148 = net.addPlace(0.0);
    _P149 = net.addPlace(0.0);
    _P150 = net.addPlace(0.0);
    _P151 = net.addPlace(0.0);
    _P152 = net.addPlace(0.0);
    _P153 = net.addPlace(50.0);
    _P154 = net.addPlace(50.0);
    _P155 = net.addPlace(50.0);
    _P156 = net.addPlace(50.0);
    _P157 = net.addPlace(50.0);
    _P158 = net.addPlace(0.0);
    _P159 = net.addPlace(0.0);
    _P160 = net.addPlace(0.0);
    _P162 = net.addPlace(50.0);
    _P163 = net.addPlace(50.0);
    _P164 = net.addPlace(0.0);
    _P166 = net.addPlace(50.0);
    _P168 = net.addPlace(50.0);
    _P170 = net.addPlace(0.0);
    _P171 = net.addPlace(50.0);
    _P175 = net.addPlace(0.0);
    nameStore.addPlaceName(iP8, "iP8" );
    nameStore.addPlaceName(iP7, "iP7" );
    nameStore.addPlaceName(iP9, "iP9" );
    nameStore.addPlaceName(iP10, "iP10" );
    nameStore.addPlaceName(iP11, "iP11" );
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(iP2, "iP2" );
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(iP4, "iP4" );
    nameStore.addPlaceName(iP3, "iP3" );
    nameStore.addPlaceName(iP6, "iP6" );
    nameStore.addPlaceName(iP5, "iP5" );
    nameStore.addPlaceName(_P3, "_P3" );
    nameStore.addPlaceName(_P4, "_P4" );
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
    nameStore.addPlaceName(_P41, "_P41" );
    nameStore.addPlaceName(_P42, "_P42" );
    nameStore.addPlaceName(_P43, "_P43" );
    nameStore.addPlaceName(_P44, "_P44" );
    nameStore.addPlaceName(_P45, "_P45" );
    nameStore.addPlaceName(_P46, "_P46" );
    nameStore.addPlaceName(_P47, "_P47" );
    nameStore.addPlaceName(_P48, "_P48" );
    nameStore.addPlaceName(_P49, "_P49" );
    nameStore.addPlaceName(_P50, "_P50" );
    nameStore.addPlaceName(_P51, "_P51" );
    nameStore.addPlaceName(_P52, "_P52" );
    nameStore.addPlaceName(_P53, "_P53" );
    nameStore.addPlaceName(_P54, "_P54" );
    nameStore.addPlaceName(_P55, "_P55" );
    nameStore.addPlaceName(_P56, "_P56" );
    nameStore.addPlaceName(_P57, "_P57" );
    nameStore.addPlaceName(_P58, "_P58" );
    nameStore.addPlaceName(_P59, "_P59" );
    nameStore.addPlaceName(_P60, "_P60" );
    nameStore.addPlaceName(_P61, "_P61" );
    nameStore.addPlaceName(_P62, "_P62" );
    nameStore.addPlaceName(_P63, "_P63" );
    nameStore.addPlaceName(_P64, "_P64" );
    nameStore.addPlaceName(_P65, "_P65" );
    nameStore.addPlaceName(_P66, "_P66" );
    nameStore.addPlaceName(_P67, "_P67" );
    nameStore.addPlaceName(_P68, "_P68" );
    nameStore.addPlaceName(_P69, "_P69" );
    nameStore.addPlaceName(_P70, "_P70" );
    nameStore.addPlaceName(_P71, "_P71" );
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
    nameStore.addPlaceName(P21, "P21" );
    nameStore.addPlaceName(_P89, "_P89" );
    nameStore.addPlaceName(P20, "P20" );
    nameStore.addPlaceName(_P91, "_P91" );
    nameStore.addPlaceName(P23, "P23" );
    nameStore.addPlaceName(P22, "P22" );
    nameStore.addPlaceName(P25, "P25" );
    nameStore.addPlaceName(P24, "P24" );
    nameStore.addPlaceName(_P96, "_P96" );
    nameStore.addPlaceName(P27, "P27" );
    nameStore.addPlaceName(P26, "P26" );
    nameStore.addPlaceName(_P99, "_P99" );
    nameStore.addPlaceName(_P100, "_P100" );
    nameStore.addPlaceName(_P101, "_P101" );
    nameStore.addPlaceName(_P102, "_P102" );
    nameStore.addPlaceName(_P103, "_P103" );
    nameStore.addPlaceName(_P104, "_P104" );
    nameStore.addPlaceName(_P105, "_P105" );
    nameStore.addPlaceName(P30, "P30" );
    nameStore.addPlaceName(P32, "P32" );
    nameStore.addPlaceName(P31, "P31" );
    nameStore.addPlaceName(P33, "P33" );
    nameStore.addPlaceName(_P110, "_P110" );
    nameStore.addPlaceName(_P111, "_P111" );
    nameStore.addPlaceName(_P112, "_P112" );
    nameStore.addPlaceName(_P113, "_P113" );
    nameStore.addPlaceName(_P114, "_P114" );
    nameStore.addPlaceName(_P115, "_P115" );
    nameStore.addPlaceName(_P116, "_P116" );
    nameStore.addPlaceName(_P117, "_P117" );
    nameStore.addPlaceName(_P118, "_P118" );
    nameStore.addPlaceName(_P119, "_P119" );
    nameStore.addPlaceName(_P120, "_P120" );
    nameStore.addPlaceName(_P121, "_P121" );
    nameStore.addPlaceName(P41, "P41" );
    nameStore.addPlaceName(P40, "P40" );
    nameStore.addPlaceName(P43, "P43" );
    nameStore.addPlaceName(P42, "P42" );
    nameStore.addPlaceName(_P126, "_P126" );
    nameStore.addPlaceName(_P127, "_P127" );
    nameStore.addPlaceName(_P128, "_P128" );
    nameStore.addPlaceName(_P129, "_P129" );
    nameStore.addPlaceName(_P130, "_P130" );
    nameStore.addPlaceName(_P131, "_P131" );
    nameStore.addPlaceName(_P132, "_P132" );
    nameStore.addPlaceName(_P133, "_P133" );
    nameStore.addPlaceName(_P134, "_P134" );
    nameStore.addPlaceName(_P135, "_P135" );
    nameStore.addPlaceName(_P136, "_P136" );
    nameStore.addPlaceName(_P137, "_P137" );
    nameStore.addPlaceName(_P138, "_P138" );
    nameStore.addPlaceName(_P139, "_P139" );
    nameStore.addPlaceName(_P140, "_P140" );
    nameStore.addPlaceName(_P141, "_P141" );
    nameStore.addPlaceName(_P142, "_P142" );
    nameStore.addPlaceName(_P143, "_P143" );
    nameStore.addPlaceName(_P144, "_P144" );
    nameStore.addPlaceName(_P145, "_P145" );
    nameStore.addPlaceName(_P146, "_P146" );
    nameStore.addPlaceName(_P147, "_P147" );
    nameStore.addPlaceName(_P148, "_P148" );
    nameStore.addPlaceName(_P149, "_P149" );
    nameStore.addPlaceName(_P150, "_P150" );
    nameStore.addPlaceName(_P151, "_P151" );
    nameStore.addPlaceName(_P152, "_P152" );
    nameStore.addPlaceName(_P153, "_P153" );
    nameStore.addPlaceName(_P154, "_P154" );
    nameStore.addPlaceName(_P155, "_P155" );
    nameStore.addPlaceName(_P156, "_P156" );
    nameStore.addPlaceName(_P157, "_P157" );
    nameStore.addPlaceName(_P158, "_P158" );
    nameStore.addPlaceName(_P159, "_P159" );
    nameStore.addPlaceName(_P160, "_P160" );
    nameStore.addPlaceName(_P162, "_P162" );
    nameStore.addPlaceName(_P163, "_P163" );
    nameStore.addPlaceName(_P164, "_P164" );
    nameStore.addPlaceName(_P166, "_P166" );
    nameStore.addPlaceName(_P168, "_P168" );
    nameStore.addPlaceName(_P170, "_P170" );
    nameStore.addPlaceName(_P171, "_P171" );
    nameStore.addPlaceName(_P175, "_P175" );
  }

  private void addTransitions(){
    oT6 =  net.addOuputTransition(parser.parseOneXOneTable(oT6_tableStr));
    oT5 =  net.addOuputTransition(parser.parseOneXOneTable(oT5_tableStr));
    oT8 =  net.addOuputTransition(parser.parseOneXOneTable(oT8_tableStr));
    oT7 =  net.addOuputTransition(parser.parseOneXOneTable(oT7_tableStr));
    oT2 =  net.addOuputTransition(parser.parseOneXOneTable(oT2_tableStr));
    oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
    oT4 =  net.addOuputTransition(parser.parseOneXOneTable(oT4_tableStr));
    oT3 =  net.addOuputTransition(parser.parseOneXOneTable(oT3_tableStr));
    nameStore.addTransitionName(oT6, "oT6" );
    nameStore.addTransitionName(oT5, "oT5" );
    nameStore.addTransitionName(oT8, "oT8" );
    nameStore.addTransitionName(oT7, "oT7" );
    nameStore.addTransitionName(oT2, "oT2" );
    nameStore.addTransitionName(oT1, "oT1" );
    nameStore.addTransitionName(oT4, "oT4" );
    nameStore.addTransitionName(oT3, "oT3" );
    qThree_iLaneOne_T2 = net.addTransition(0, parser.parseTable(qThree_iLaneOne_T2_tableStr));
    qFour_calcOne_oT9 = net.addTransition(0, parser.parseTable(qFour_calcOne_oT9_tableStr));
    qThree_splitOne_oT6 = net.addTransition(0, parser.parseTable(qThree_splitOne_oT6_tableStr));
    qThree_splitOne_oT5 = net.addTransition(0, parser.parseTable(qThree_splitOne_oT5_tableStr));
    qOne_calcOne_T2 = net.addTransition(1, parser.parseTable(qOne_calcOne_T2_tableStr));
    qOne_calcOne_T3 = net.addTransition(0, parser.parseTable(qOne_calcOne_T3_tableStr));
    qOne_calcOne_T8 = net.addTransition(0, parser.parseTable(qOne_calcOne_T8_tableStr));
    qTwo_iLaneOne_oT3 = net.addTransition(0, parser.parseTable(qTwo_iLaneOne_oT3_tableStr));
    qOne_calcOne_T7 = net.addTransition(0, parser.parseTable(qOne_calcOne_T7_tableStr));
    qOne_oT3 = net.addTransition(0, parser.parseTable(qOne_oT3_tableStr));
    qThree_oT2 = net.addTransition(0, parser.parseTable(qThree_oT2_tableStr));
    qOne_oT2 = net.addTransition(0, parser.parseTable(qOne_oT2_tableStr));
    qThree_oT3 = net.addTransition(0, parser.parseTable(qThree_oT3_tableStr));
    qOne_oT1 = net.addTransition(0, parser.parseTable(qOne_oT1_tableStr));
    qThree_iLaneOne_T1 = net.addTransition(0, parser.parseTable(qThree_iLaneOne_T1_tableStr));
    qThree_oT1 = net.addTransition(0, parser.parseTable(qThree_oT1_tableStr));
    qThree_iLaneOne_T0 = net.addTransition(0, parser.parseTable(qThree_iLaneOne_T0_tableStr));
    qOne_calcOne_T0 = net.addTransition(0, parser.parseTable(qOne_calcOne_T0_tableStr));
    qOne_calcOne_T1 = net.addTransition(0, parser.parseTable(qOne_calcOne_T1_tableStr));
    qOne_calcOne_T10 = net.addTransition(0, parser.parseTable(qOne_calcOne_T10_tableStr));
    qTwo_oT1 = net.addTransition(0, parser.parseTable(qTwo_oT1_tableStr));
    qThree_calcOne_oT9 = net.addTransition(0, parser.parseTable(qThree_calcOne_oT9_tableStr));
    qFour_splitOne_oT5 = net.addTransition(0, parser.parseTable(qFour_splitOne_oT5_tableStr));
    qFour_splitOne_oT6 = net.addTransition(0, parser.parseTable(qFour_splitOne_oT6_tableStr));
    qOne_splitOne_T2 = net.addTransition(0, parser.parseTable(qOne_splitOne_T2_tableStr));
    qOne_splitOne_T1 = net.addTransition(0, parser.parseTable(qOne_splitOne_T1_tableStr));
    qOne_splitOne_T0 = net.addTransition(0, parser.parseTable(qOne_splitOne_T0_tableStr));
    qFour_oT3 = net.addTransition(0, parser.parseTable(qFour_oT3_tableStr));
    qFour_oT2 = net.addTransition(0, parser.parseTable(qFour_oT2_tableStr));
    qFour_oT1 = net.addTransition(0, parser.parseTable(qFour_oT1_tableStr));
    qFour_iLaneOne_T2 = net.addTransition(0, parser.parseTable(qFour_iLaneOne_T2_tableStr));
    qFour_iLaneOne_T1 = net.addTransition(0, parser.parseTable(qFour_iLaneOne_T1_tableStr));
    qFour_iLaneOne_T0 = net.addTransition(0, parser.parseTable(qFour_iLaneOne_T0_tableStr));
    qThree_calcOne_T10 = net.addTransition(0, parser.parseTable(qThree_calcOne_T10_tableStr));
    qOne_T0 = net.addTransition(0, parser.parseTable(qOne_T0_tableStr));
    qFour_calcOne_T3 = net.addTransition(0, parser.parseTable(qFour_calcOne_T3_tableStr));
    qFour_calcOne_T2 = net.addTransition(1, parser.parseTable(qFour_calcOne_T2_tableStr));
    qOne_T2 = net.addTransition(0, parser.parseTable(qOne_T2_tableStr));
    qFour_calcOne_T1 = net.addTransition(0, parser.parseTable(qFour_calcOne_T1_tableStr));
    qOne_T1 = net.addTransition(0, parser.parseTable(qOne_T1_tableStr));
    qFour_calcOne_T0 = net.addTransition(0, parser.parseTable(qFour_calcOne_T0_tableStr));
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    qOne_T4 = net.addTransition(0, parser.parseTable(qOne_T4_tableStr));
    qFour_calcOne_T7 = net.addTransition(0, parser.parseTable(qFour_calcOne_T7_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    qOne_T3 = net.addTransition(0, parser.parseTable(qOne_T3_tableStr));
    T2 = net.addTransition(0, parser.parseTable(T2_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    T4 = net.addTransition(0, parser.parseTable(T4_tableStr));
    T5 = net.addTransition(0, parser.parseTable(T5_tableStr));
    T6 = net.addTransition(0, parser.parseTable(T6_tableStr));
    T7 = net.addTransition(0, parser.parseTable(T7_tableStr));
    qFour_calcOne_T8 = net.addTransition(0, parser.parseTable(qFour_calcOne_T8_tableStr));
    T8 = net.addTransition(0, parser.parseTable(T8_tableStr));
    T9 = net.addTransition(0, parser.parseTable(T9_tableStr));
    qTwo_calcOne_T10 = net.addTransition(0, parser.parseTable(qTwo_calcOne_T10_tableStr));
    qFour_calcOne_T10 = net.addTransition(0, parser.parseTable(qFour_calcOne_T10_tableStr));
    qOne_calcOne_oT14 = net.addTransition(0, parser.parseTable(qOne_calcOne_oT14_tableStr));
    qTwo_oT2 = net.addTransition(0, parser.parseTable(qTwo_oT2_tableStr));
    qOne_calcOne_oT13 = net.addTransition(0, parser.parseTable(qOne_calcOne_oT13_tableStr));
    qTwo_oT3 = net.addTransition(0, parser.parseTable(qTwo_oT3_tableStr));
    qOne_splitOne_oT5 = net.addTransition(0, parser.parseTable(qOne_splitOne_oT5_tableStr));
    qTwo_calcOne_T8 = net.addTransition(0, parser.parseTable(qTwo_calcOne_T8_tableStr));
    T10 = net.addTransition(0, parser.parseTable(T10_tableStr));
    qOne_splitOne_oT6 = net.addTransition(0, parser.parseTable(qOne_splitOne_oT6_tableStr));
    qTwo_iLaneOne_T0 = net.addTransition(0, parser.parseTable(qTwo_iLaneOne_T0_tableStr));
    T11 = net.addTransition(0, parser.parseTable(T11_tableStr));
    qTwo_iLaneOne_T1 = net.addTransition(0, parser.parseTable(qTwo_iLaneOne_T1_tableStr));
    qTwo_iLaneOne_T2 = net.addTransition(0, parser.parseTable(qTwo_iLaneOne_T2_tableStr));
    qTwo_calcOne_T7 = net.addTransition(0, parser.parseTable(qTwo_calcOne_T7_tableStr));
    qThree_calcOne_T1 = net.addTransition(0, parser.parseTable(qThree_calcOne_T1_tableStr));
    qThree_calcOne_T0 = net.addTransition(0, parser.parseTable(qThree_calcOne_T0_tableStr));
    qThree_calcOne_T3 = net.addTransition(0, parser.parseTable(qThree_calcOne_T3_tableStr));
    qThree_calcOne_T2 = net.addTransition(1, parser.parseTable(qThree_calcOne_T2_tableStr));
    qThree_calcOne_T7 = net.addTransition(0, parser.parseTable(qThree_calcOne_T7_tableStr));
    qThree_calcOne_T8 = net.addTransition(0, parser.parseTable(qThree_calcOne_T8_tableStr));
    qTwo_calcOne_T1 = net.addTransition(0, parser.parseTable(qTwo_calcOne_T1_tableStr));
    qTwo_calcOne_T0 = net.addTransition(0, parser.parseTable(qTwo_calcOne_T0_tableStr));
    qTwo_calcOne_T3 = net.addTransition(0, parser.parseTable(qTwo_calcOne_T3_tableStr));
    qTwo_calcOne_T2 = net.addTransition(1, parser.parseTable(qTwo_calcOne_T2_tableStr));
    T21 = net.addTransition(0, parser.parseTable(T21_tableStr));
    T20 = net.addTransition(0, parser.parseTable(T20_tableStr));
    T23 = net.addTransition(0, parser.parseTable(T23_tableStr));
    T22 = net.addTransition(0, parser.parseTable(T22_tableStr));
    qTwo_T1 = net.addTransition(0, parser.parseTable(qTwo_T1_tableStr));
    qTwo_T2 = net.addTransition(0, parser.parseTable(qTwo_T2_tableStr));
    qTwo_T3 = net.addTransition(0, parser.parseTable(qTwo_T3_tableStr));
    qTwo_T4 = net.addTransition(0, parser.parseTable(qTwo_T4_tableStr));
    qTwo_T0 = net.addTransition(0, parser.parseTable(qTwo_T0_tableStr));
    qTwo_splitOne_oT6 = net.addTransition(0, parser.parseTable(qTwo_splitOne_oT6_tableStr));
    qTwo_splitOne_oT5 = net.addTransition(0, parser.parseTable(qTwo_splitOne_oT5_tableStr));
    qFour_calcOne_oT14 = net.addTransition(0, parser.parseTable(qFour_calcOne_oT14_tableStr));
    qFour_calcOne_oT13 = net.addTransition(0, parser.parseTable(qFour_calcOne_oT13_tableStr));
    qTwo_calcOne_oT9 = net.addTransition(0, parser.parseTable(qTwo_calcOne_oT9_tableStr));
    qOne_iLaneOne_oT3 = net.addTransition(0, parser.parseTable(qOne_iLaneOne_oT3_tableStr));
    qOne_calcOne_oT9 = net.addTransition(0, parser.parseTable(qOne_calcOne_oT9_tableStr));
    qFour_iLaneOne_oT3 = net.addTransition(0, parser.parseTable(qFour_iLaneOne_oT3_tableStr));
    qTwo_splitOne_T7 = net.addTransition(0, parser.parseTable(qTwo_splitOne_T7_tableStr));
    qTwo_splitOne_T0 = net.addTransition(0, parser.parseTable(qTwo_splitOne_T0_tableStr));
    qOne_splitOne_T7 = net.addTransition(0, parser.parseTable(qOne_splitOne_T7_tableStr));
    qThree_calcOne_oT13 = net.addTransition(0, parser.parseTable(qThree_calcOne_oT13_tableStr));
    qTwo_splitOne_T2 = net.addTransition(0, parser.parseTable(qTwo_splitOne_T2_tableStr));
    qTwo_splitOne_T1 = net.addTransition(0, parser.parseTable(qTwo_splitOne_T1_tableStr));
    qOne_splitOne_T4 = net.addTransition(0, parser.parseTable(qOne_splitOne_T4_tableStr));
    qTwo_splitOne_T4 = net.addTransition(0, parser.parseTable(qTwo_splitOne_T4_tableStr));
    qOne_splitOne_T3 = net.addTransition(0, parser.parseTable(qOne_splitOne_T3_tableStr));
    qThree_calcOne_oT14 = net.addTransition(0, parser.parseTable(qThree_calcOne_oT14_tableStr));
    qTwo_splitOne_T3 = net.addTransition(0, parser.parseTable(qTwo_splitOne_T3_tableStr));
    qThree_splitOne_T1 = net.addTransition(0, parser.parseTable(qThree_splitOne_T1_tableStr));
    qThree_splitOne_T2 = net.addTransition(0, parser.parseTable(qThree_splitOne_T2_tableStr));
    qThree_splitOne_T0 = net.addTransition(0, parser.parseTable(qThree_splitOne_T0_tableStr));
    qThree_iLaneOne_oT3 = net.addTransition(0, parser.parseTable(qThree_iLaneOne_oT3_tableStr));
    qThree_splitOne_T7 = net.addTransition(0, parser.parseTable(qThree_splitOne_T7_tableStr));
    qThree_splitOne_T3 = net.addTransition(0, parser.parseTable(qThree_splitOne_T3_tableStr));
    qThree_splitOne_T4 = net.addTransition(0, parser.parseTable(qThree_splitOne_T4_tableStr));
    qFour_T3 = net.addTransition(0, parser.parseTable(qFour_T3_tableStr));
    qFour_T4 = net.addTransition(0, parser.parseTable(qFour_T4_tableStr));
    qFour_T1 = net.addTransition(0, parser.parseTable(qFour_T1_tableStr));
    qFour_T2 = net.addTransition(0, parser.parseTable(qFour_T2_tableStr));
    qFour_T0 = net.addTransition(0, parser.parseTable(qFour_T0_tableStr));
    qOne_iLaneOne_T2 = net.addTransition(0, parser.parseTable(qOne_iLaneOne_T2_tableStr));
    qOne_iLaneOne_T0 = net.addTransition(0, parser.parseTable(qOne_iLaneOne_T0_tableStr));
    qFour_splitOne_T0 = net.addTransition(0, parser.parseTable(qFour_splitOne_T0_tableStr));
    qOne_iLaneOne_T1 = net.addTransition(0, parser.parseTable(qOne_iLaneOne_T1_tableStr));
    qThree_T0 = net.addTransition(0, parser.parseTable(qThree_T0_tableStr));
    qThree_T1 = net.addTransition(0, parser.parseTable(qThree_T1_tableStr));
    qThree_T2 = net.addTransition(0, parser.parseTable(qThree_T2_tableStr));
    qThree_T3 = net.addTransition(0, parser.parseTable(qThree_T3_tableStr));
    qThree_T4 = net.addTransition(0, parser.parseTable(qThree_T4_tableStr));
    qTwo_calcOne_oT14 = net.addTransition(0, parser.parseTable(qTwo_calcOne_oT14_tableStr));
    qFour_splitOne_T7 = net.addTransition(0, parser.parseTable(qFour_splitOne_T7_tableStr));
    qFour_splitOne_T4 = net.addTransition(0, parser.parseTable(qFour_splitOne_T4_tableStr));
    qFour_splitOne_T3 = net.addTransition(0, parser.parseTable(qFour_splitOne_T3_tableStr));
    qFour_splitOne_T2 = net.addTransition(0, parser.parseTable(qFour_splitOne_T2_tableStr));
    qFour_splitOne_T1 = net.addTransition(0, parser.parseTable(qFour_splitOne_T1_tableStr));
    qTwo_calcOne_oT13 = net.addTransition(0, parser.parseTable(qTwo_calcOne_oT13_tableStr));
    nameStore.addTransitionName(qThree_iLaneOne_T2, "qThree_iLaneOne_T2" );
    nameStore.addTransitionName(qFour_calcOne_oT9, "qFour_calcOne_oT9" );
    nameStore.addTransitionName(qThree_splitOne_oT6, "qThree_splitOne_oT6" );
    nameStore.addTransitionName(qThree_splitOne_oT5, "qThree_splitOne_oT5" );
    nameStore.addTransitionName(qOne_calcOne_T2, "qOne_calcOne_T2" );
    nameStore.addTransitionName(qOne_calcOne_T3, "qOne_calcOne_T3" );
    nameStore.addTransitionName(qOne_calcOne_T8, "qOne_calcOne_T8" );
    nameStore.addTransitionName(qTwo_iLaneOne_oT3, "qTwo_iLaneOne_oT3" );
    nameStore.addTransitionName(qOne_calcOne_T7, "qOne_calcOne_T7" );
    nameStore.addTransitionName(qOne_oT3, "qOne_oT3" );
    nameStore.addTransitionName(qThree_oT2, "qThree_oT2" );
    nameStore.addTransitionName(qOne_oT2, "qOne_oT2" );
    nameStore.addTransitionName(qThree_oT3, "qThree_oT3" );
    nameStore.addTransitionName(qOne_oT1, "qOne_oT1" );
    nameStore.addTransitionName(qThree_iLaneOne_T1, "qThree_iLaneOne_T1" );
    nameStore.addTransitionName(qThree_oT1, "qThree_oT1" );
    nameStore.addTransitionName(qThree_iLaneOne_T0, "qThree_iLaneOne_T0" );
    nameStore.addTransitionName(qOne_calcOne_T0, "qOne_calcOne_T0" );
    nameStore.addTransitionName(qOne_calcOne_T1, "qOne_calcOne_T1" );
    nameStore.addTransitionName(qOne_calcOne_T10, "qOne_calcOne_T10" );
    nameStore.addTransitionName(qTwo_oT1, "qTwo_oT1" );
    nameStore.addTransitionName(qThree_calcOne_oT9, "qThree_calcOne_oT9" );
    nameStore.addTransitionName(qFour_splitOne_oT5, "qFour_splitOne_oT5" );
    nameStore.addTransitionName(qFour_splitOne_oT6, "qFour_splitOne_oT6" );
    nameStore.addTransitionName(qOne_splitOne_T2, "qOne_splitOne_T2" );
    nameStore.addTransitionName(qOne_splitOne_T1, "qOne_splitOne_T1" );
    nameStore.addTransitionName(qOne_splitOne_T0, "qOne_splitOne_T0" );
    nameStore.addTransitionName(qFour_oT3, "qFour_oT3" );
    nameStore.addTransitionName(qFour_oT2, "qFour_oT2" );
    nameStore.addTransitionName(qFour_oT1, "qFour_oT1" );
    nameStore.addTransitionName(qFour_iLaneOne_T2, "qFour_iLaneOne_T2" );
    nameStore.addTransitionName(qFour_iLaneOne_T1, "qFour_iLaneOne_T1" );
    nameStore.addTransitionName(qFour_iLaneOne_T0, "qFour_iLaneOne_T0" );
    nameStore.addTransitionName(qThree_calcOne_T10, "qThree_calcOne_T10" );
    nameStore.addTransitionName(qOne_T0, "qOne_T0" );
    nameStore.addTransitionName(qFour_calcOne_T3, "qFour_calcOne_T3" );
    nameStore.addTransitionName(qFour_calcOne_T2, "qFour_calcOne_T2" );
    nameStore.addTransitionName(qOne_T2, "qOne_T2" );
    nameStore.addTransitionName(qFour_calcOne_T1, "qFour_calcOne_T1" );
    nameStore.addTransitionName(qOne_T1, "qOne_T1" );
    nameStore.addTransitionName(qFour_calcOne_T0, "qFour_calcOne_T0" );
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(qOne_T4, "qOne_T4" );
    nameStore.addTransitionName(qFour_calcOne_T7, "qFour_calcOne_T7" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(qOne_T3, "qOne_T3" );
    nameStore.addTransitionName(T2, "T2" );
    nameStore.addTransitionName(T3, "T3" );
    nameStore.addTransitionName(T4, "T4" );
    nameStore.addTransitionName(T5, "T5" );
    nameStore.addTransitionName(T6, "T6" );
    nameStore.addTransitionName(T7, "T7" );
    nameStore.addTransitionName(qFour_calcOne_T8, "qFour_calcOne_T8" );
    nameStore.addTransitionName(T8, "T8" );
    nameStore.addTransitionName(T9, "T9" );
    nameStore.addTransitionName(qTwo_calcOne_T10, "qTwo_calcOne_T10" );
    nameStore.addTransitionName(qFour_calcOne_T10, "qFour_calcOne_T10" );
    nameStore.addTransitionName(qOne_calcOne_oT14, "qOne_calcOne_oT14" );
    nameStore.addTransitionName(qTwo_oT2, "qTwo_oT2" );
    nameStore.addTransitionName(qOne_calcOne_oT13, "qOne_calcOne_oT13" );
    nameStore.addTransitionName(qTwo_oT3, "qTwo_oT3" );
    nameStore.addTransitionName(qOne_splitOne_oT5, "qOne_splitOne_oT5" );
    nameStore.addTransitionName(qTwo_calcOne_T8, "qTwo_calcOne_T8" );
    nameStore.addTransitionName(T10, "T10" );
    nameStore.addTransitionName(qOne_splitOne_oT6, "qOne_splitOne_oT6" );
    nameStore.addTransitionName(qTwo_iLaneOne_T0, "qTwo_iLaneOne_T0" );
    nameStore.addTransitionName(T11, "T11" );
    nameStore.addTransitionName(qTwo_iLaneOne_T1, "qTwo_iLaneOne_T1" );
    nameStore.addTransitionName(qTwo_iLaneOne_T2, "qTwo_iLaneOne_T2" );
    nameStore.addTransitionName(qTwo_calcOne_T7, "qTwo_calcOne_T7" );
    nameStore.addTransitionName(qThree_calcOne_T1, "qThree_calcOne_T1" );
    nameStore.addTransitionName(qThree_calcOne_T0, "qThree_calcOne_T0" );
    nameStore.addTransitionName(qThree_calcOne_T3, "qThree_calcOne_T3" );
    nameStore.addTransitionName(qThree_calcOne_T2, "qThree_calcOne_T2" );
    nameStore.addTransitionName(qThree_calcOne_T7, "qThree_calcOne_T7" );
    nameStore.addTransitionName(qThree_calcOne_T8, "qThree_calcOne_T8" );
    nameStore.addTransitionName(qTwo_calcOne_T1, "qTwo_calcOne_T1" );
    nameStore.addTransitionName(qTwo_calcOne_T0, "qTwo_calcOne_T0" );
    nameStore.addTransitionName(qTwo_calcOne_T3, "qTwo_calcOne_T3" );
    nameStore.addTransitionName(qTwo_calcOne_T2, "qTwo_calcOne_T2" );
    nameStore.addTransitionName(T21, "T21" );
    nameStore.addTransitionName(T20, "T20" );
    nameStore.addTransitionName(T23, "T23" );
    nameStore.addTransitionName(T22, "T22" );
    nameStore.addTransitionName(qTwo_T1, "qTwo_T1" );
    nameStore.addTransitionName(qTwo_T2, "qTwo_T2" );
    nameStore.addTransitionName(qTwo_T3, "qTwo_T3" );
    nameStore.addTransitionName(qTwo_T4, "qTwo_T4" );
    nameStore.addTransitionName(qTwo_T0, "qTwo_T0" );
    nameStore.addTransitionName(qTwo_splitOne_oT6, "qTwo_splitOne_oT6" );
    nameStore.addTransitionName(qTwo_splitOne_oT5, "qTwo_splitOne_oT5" );
    nameStore.addTransitionName(qFour_calcOne_oT14, "qFour_calcOne_oT14" );
    nameStore.addTransitionName(qFour_calcOne_oT13, "qFour_calcOne_oT13" );
    nameStore.addTransitionName(qTwo_calcOne_oT9, "qTwo_calcOne_oT9" );
    nameStore.addTransitionName(qOne_iLaneOne_oT3, "qOne_iLaneOne_oT3" );
    nameStore.addTransitionName(qOne_calcOne_oT9, "qOne_calcOne_oT9" );
    nameStore.addTransitionName(qFour_iLaneOne_oT3, "qFour_iLaneOne_oT3" );
    nameStore.addTransitionName(qTwo_splitOne_T7, "qTwo_splitOne_T7" );
    nameStore.addTransitionName(qTwo_splitOne_T0, "qTwo_splitOne_T0" );
    nameStore.addTransitionName(qOne_splitOne_T7, "qOne_splitOne_T7" );
    nameStore.addTransitionName(qThree_calcOne_oT13, "qThree_calcOne_oT13" );
    nameStore.addTransitionName(qTwo_splitOne_T2, "qTwo_splitOne_T2" );
    nameStore.addTransitionName(qTwo_splitOne_T1, "qTwo_splitOne_T1" );
    nameStore.addTransitionName(qOne_splitOne_T4, "qOne_splitOne_T4" );
    nameStore.addTransitionName(qTwo_splitOne_T4, "qTwo_splitOne_T4" );
    nameStore.addTransitionName(qOne_splitOne_T3, "qOne_splitOne_T3" );
    nameStore.addTransitionName(qThree_calcOne_oT14, "qThree_calcOne_oT14" );
    nameStore.addTransitionName(qTwo_splitOne_T3, "qTwo_splitOne_T3" );
    nameStore.addTransitionName(qThree_splitOne_T1, "qThree_splitOne_T1" );
    nameStore.addTransitionName(qThree_splitOne_T2, "qThree_splitOne_T2" );
    nameStore.addTransitionName(qThree_splitOne_T0, "qThree_splitOne_T0" );
    nameStore.addTransitionName(qThree_iLaneOne_oT3, "qThree_iLaneOne_oT3" );
    nameStore.addTransitionName(qThree_splitOne_T7, "qThree_splitOne_T7" );
    nameStore.addTransitionName(qThree_splitOne_T3, "qThree_splitOne_T3" );
    nameStore.addTransitionName(qThree_splitOne_T4, "qThree_splitOne_T4" );
    nameStore.addTransitionName(qFour_T3, "qFour_T3" );
    nameStore.addTransitionName(qFour_T4, "qFour_T4" );
    nameStore.addTransitionName(qFour_T1, "qFour_T1" );
    nameStore.addTransitionName(qFour_T2, "qFour_T2" );
    nameStore.addTransitionName(qFour_T0, "qFour_T0" );
    nameStore.addTransitionName(qOne_iLaneOne_T2, "qOne_iLaneOne_T2" );
    nameStore.addTransitionName(qOne_iLaneOne_T0, "qOne_iLaneOne_T0" );
    nameStore.addTransitionName(qFour_splitOne_T0, "qFour_splitOne_T0" );
    nameStore.addTransitionName(qOne_iLaneOne_T1, "qOne_iLaneOne_T1" );
    nameStore.addTransitionName(qThree_T0, "qThree_T0" );
    nameStore.addTransitionName(qThree_T1, "qThree_T1" );
    nameStore.addTransitionName(qThree_T2, "qThree_T2" );
    nameStore.addTransitionName(qThree_T3, "qThree_T3" );
    nameStore.addTransitionName(qThree_T4, "qThree_T4" );
    nameStore.addTransitionName(qTwo_calcOne_oT14, "qTwo_calcOne_oT14" );
    nameStore.addTransitionName(qFour_splitOne_T7, "qFour_splitOne_T7" );
    nameStore.addTransitionName(qFour_splitOne_T4, "qFour_splitOne_T4" );
    nameStore.addTransitionName(qFour_splitOne_T3, "qFour_splitOne_T3" );
    nameStore.addTransitionName(qFour_splitOne_T2, "qFour_splitOne_T2" );
    nameStore.addTransitionName(qFour_splitOne_T1, "qFour_splitOne_T1" );
    nameStore.addTransitionName(qTwo_calcOne_oT13, "qTwo_calcOne_oT13" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P41, oT6);
    net.addArcFromPlaceToTransition(P40, oT5);
    net.addArcFromPlaceToTransition(P43, oT8);
    net.addArcFromPlaceToTransition(P42, oT7);
    net.addArcFromPlaceToTransition(_P99, qThree_iLaneOne_T2);
    net.addArcFromPlaceToTransition(_P155, qThree_iLaneOne_T2);
    net.addArcFromPlaceToTransition(_P143, qFour_calcOne_oT9);
    net.addArcFromPlaceToTransition(_P32, qThree_splitOne_oT6);
    net.addArcFromPlaceToTransition(_P60, qThree_splitOne_oT5);
    net.addArcFromPlaceToTransition(_P103, qOne_calcOne_T2);
    net.addArcFromPlaceToTransition(_P103, qOne_calcOne_T3);
    net.addArcFromPlaceToTransition(_P131, qOne_calcOne_T3);
    net.addArcFromPlaceToTransition(_P114, qOne_calcOne_T8);
    net.addArcFromPlaceToTransition(_P171, qTwo_iLaneOne_oT3);
    net.addArcFromPlaceToTransition(_P113, qOne_calcOne_T7);
    net.addArcFromPlaceToTransition(_P111, qOne_calcOne_T7);
    net.addArcFromPlaceToTransition(_P130, qOne_oT3);
    net.addArcFromPlaceToTransition(_P81, qThree_oT2);
    net.addArcFromPlaceToTransition(_P132, qOne_oT2);
    net.addArcFromPlaceToTransition(_P82, qThree_oT3);
    net.addArcFromPlaceToTransition(_P129, qOne_oT1);
    net.addArcFromPlaceToTransition(_P154, qThree_iLaneOne_T1);
    net.addArcFromPlaceToTransition(_P99, qThree_iLaneOne_T1);
    net.addArcFromPlaceToTransition(_P80, qThree_oT1);
    net.addArcFromPlaceToTransition(_P99, qThree_iLaneOne_T0);
    net.addArcFromPlaceToTransition(_P153, qThree_iLaneOne_T0);
    net.addArcFromPlaceToTransition(_P101, qOne_calcOne_T0);
    net.addArcFromPlaceToTransition(_P134, qOne_calcOne_T0);
    net.addArcFromPlaceToTransition(_P102, qOne_calcOne_T1);
    net.addArcFromPlaceToTransition(_P29, qOne_calcOne_T10);
    net.addArcFromPlaceToTransition(_P18, qOne_calcOne_T10);
    net.addArcFromPlaceToTransition(_P5, qTwo_oT1);
    net.addArcFromPlaceToTransition(_P166, qThree_calcOne_oT9);
    net.addArcFromPlaceToTransition(_P89, qFour_splitOne_oT5);
    net.addArcFromPlaceToTransition(_P86, qFour_splitOne_oT6);
    net.addArcFromPlaceToTransition(_P55, qOne_splitOne_T2);
    net.addArcFromPlaceToTransition(_P53, qOne_splitOne_T2);
    net.addArcFromPlaceToTransition(_P84, qOne_splitOne_T1);
    net.addArcFromPlaceToTransition(_P83, qOne_splitOne_T0);
    net.addArcFromPlaceToTransition(_P61, qFour_oT3);
    net.addArcFromPlaceToTransition(_P59, qFour_oT2);
    net.addArcFromPlaceToTransition(_P66, qFour_oT1);
    net.addArcFromPlaceToTransition(_P121, qFour_iLaneOne_T2);
    net.addArcFromPlaceToTransition(_P139, qFour_iLaneOne_T2);
    net.addArcFromPlaceToTransition(_P137, qFour_iLaneOne_T1);
    net.addArcFromPlaceToTransition(_P121, qFour_iLaneOne_T1);
    net.addArcFromPlaceToTransition(_P121, qFour_iLaneOne_T0);
    net.addArcFromPlaceToTransition(_P128, qFour_iLaneOne_T0);
    net.addArcFromPlaceToTransition(_P41, qThree_calcOne_T10);
    net.addArcFromPlaceToTransition(_P8, qThree_calcOne_T10);
    net.addArcFromPlaceToTransition(_P150, qOne_T0);
    net.addArcFromPlaceToTransition(_P136, qFour_calcOne_T3);
    net.addArcFromPlaceToTransition(_P175, qFour_calcOne_T3);
    net.addArcFromPlaceToTransition(_P136, qFour_calcOne_T2);
    net.addArcFromPlaceToTransition(_P20, qOne_T2);
    net.addArcFromPlaceToTransition(_P138, qFour_calcOne_T1);
    net.addArcFromPlaceToTransition(_P148, qOne_T1);
    net.addArcFromPlaceToTransition(_P140, qFour_calcOne_T0);
    net.addArcFromPlaceToTransition(_P170, qFour_calcOne_T0);
    net.addArcFromPlaceToTransition(iP0, T0);
    net.addArcFromPlaceToTransition(_P126, qOne_T4);
    net.addArcFromPlaceToTransition(_P142, qFour_calcOne_T7);
    net.addArcFromPlaceToTransition(_P144, qFour_calcOne_T7);
    net.addArcFromPlaceToTransition(iP1, T1);
    net.addArcFromPlaceToTransition(_P17, qOne_T3);
    net.addArcFromPlaceToTransition(iP2, T2);
    net.addArcFromPlaceToTransition(iP3, T3);
    net.addArcFromPlaceToTransition(iP4, T4);
    net.addArcFromPlaceToTransition(iP5, T5);
    net.addArcFromPlaceToTransition(iP6, T6);
    net.addArcFromPlaceToTransition(iP7, T7);
    net.addArcFromPlaceToTransition(_P141, qFour_calcOne_T8);
    net.addArcFromPlaceToTransition(iP8, T8);
    net.addArcFromPlaceToTransition(iP9, T9);
    net.addArcFromPlaceToTransition(_P75, qTwo_calcOne_T10);
    net.addArcFromPlaceToTransition(_P116, qTwo_calcOne_T10);
    net.addArcFromPlaceToTransition(_P69, qFour_calcOne_T10);
    net.addArcFromPlaceToTransition(_P74, qFour_calcOne_T10);
    net.addArcFromPlaceToTransition(_P22, qOne_calcOne_oT14);
    net.addArcFromPlaceToTransition(_P6, qTwo_oT2);
    net.addArcFromPlaceToTransition(_P31, qOne_calcOne_oT13);
    net.addArcFromPlaceToTransition(_P7, qTwo_oT3);
    net.addArcFromPlaceToTransition(_P51, qOne_splitOne_oT5);
    net.addArcFromPlaceToTransition(_P146, qTwo_calcOne_T8);
    net.addArcFromPlaceToTransition(iP10, T10);
    net.addArcFromPlaceToTransition(_P46, qOne_splitOne_oT6);
    net.addArcFromPlaceToTransition(_P163, qTwo_iLaneOne_T0);
    net.addArcFromPlaceToTransition(_P16, qTwo_iLaneOne_T0);
    net.addArcFromPlaceToTransition(iP11, T11);
    net.addArcFromPlaceToTransition(_P10, qTwo_iLaneOne_T1);
    net.addArcFromPlaceToTransition(_P163, qTwo_iLaneOne_T1);
    net.addArcFromPlaceToTransition(_P163, qTwo_iLaneOne_T2);
    net.addArcFromPlaceToTransition(_P11, qTwo_iLaneOne_T2);
    net.addArcFromPlaceToTransition(_P147, qTwo_calcOne_T7);
    net.addArcFromPlaceToTransition(_P157, qTwo_calcOne_T7);
    net.addArcFromPlaceToTransition(_P158, qThree_calcOne_T1);
    net.addArcFromPlaceToTransition(_P159, qThree_calcOne_T0);
    net.addArcFromPlaceToTransition(_P145, qThree_calcOne_T0);
    net.addArcFromPlaceToTransition(_P160, qThree_calcOne_T3);
    net.addArcFromPlaceToTransition(_P26, qThree_calcOne_T3);
    net.addArcFromPlaceToTransition(_P160, qThree_calcOne_T2);
    net.addArcFromPlaceToTransition(_P164, qThree_calcOne_T7);
    net.addArcFromPlaceToTransition(_P168, qThree_calcOne_T7);
    net.addArcFromPlaceToTransition(_P162, qThree_calcOne_T8);
    net.addArcFromPlaceToTransition(_P149, qTwo_calcOne_T1);
    net.addArcFromPlaceToTransition(_P151, qTwo_calcOne_T0);
    net.addArcFromPlaceToTransition(_P135, qTwo_calcOne_T0);
    net.addArcFromPlaceToTransition(_P152, qTwo_calcOne_T3);
    net.addArcFromPlaceToTransition(_P133, qTwo_calcOne_T3);
    net.addArcFromPlaceToTransition(_P152, qTwo_calcOne_T2);
    net.addArcFromPlaceToTransition(P22, T21);
    net.addArcFromPlaceToTransition(P23, T21);
    net.addArcFromPlaceToTransition(P20, T20);
    net.addArcFromPlaceToTransition(P21, T20);
    net.addArcFromPlaceToTransition(P26, T23);
    net.addArcFromPlaceToTransition(P27, T23);
    net.addArcFromPlaceToTransition(P24, T22);
    net.addArcFromPlaceToTransition(P25, T22);
    net.addArcFromPlaceToTransition(_P27, qTwo_T1);
    net.addArcFromPlaceToTransition(_P23, qTwo_T2);
    net.addArcFromPlaceToTransition(_P24, qTwo_T3);
    net.addArcFromPlaceToTransition(_P9, qTwo_T4);
    net.addArcFromPlaceToTransition(_P25, qTwo_T0);
    net.addArcFromPlaceToTransition(_P48, qTwo_splitOne_oT6);
    net.addArcFromPlaceToTransition(_P43, qTwo_splitOne_oT5);
    net.addArcFromPlaceToTransition(_P64, qFour_calcOne_oT14);
    net.addArcFromPlaceToTransition(_P62, qFour_calcOne_oT13);
    net.addArcFromPlaceToTransition(_P156, qTwo_calcOne_oT9);
    net.addArcFromPlaceToTransition(_P70, qOne_iLaneOne_oT3);
    net.addArcFromPlaceToTransition(_P112, qOne_calcOne_oT9);
    net.addArcFromPlaceToTransition(_P110, qFour_iLaneOne_oT3);
    net.addArcFromPlaceToTransition(_P118, qTwo_splitOne_T7);
    net.addArcFromPlaceToTransition(_P37, qTwo_splitOne_T7);
    net.addArcFromPlaceToTransition(_P105, qTwo_splitOne_T0);
    net.addArcFromPlaceToTransition(_P44, qOne_splitOne_T7);
    net.addArcFromPlaceToTransition(_P127, qOne_splitOne_T7);
    net.addArcFromPlaceToTransition(_P39, qThree_calcOne_oT13);
    net.addArcFromPlaceToTransition(_P38, qTwo_splitOne_T2);
    net.addArcFromPlaceToTransition(_P40, qTwo_splitOne_T2);
    net.addArcFromPlaceToTransition(_P104, qTwo_splitOne_T1);
    net.addArcFromPlaceToTransition(_P49, qOne_splitOne_T4);
    net.addArcFromPlaceToTransition(_P52, qOne_splitOne_T4);
    net.addArcFromPlaceToTransition(_P50, qTwo_splitOne_T4);
    net.addArcFromPlaceToTransition(_P45, qTwo_splitOne_T4);
    net.addArcFromPlaceToTransition(_P127, qOne_splitOne_T3);
    net.addArcFromPlaceToTransition(_P54, qOne_splitOne_T3);
    net.addArcFromPlaceToTransition(_P47, qThree_calcOne_oT14);
    net.addArcFromPlaceToTransition(_P37, qTwo_splitOne_T3);
    net.addArcFromPlaceToTransition(_P42, qTwo_splitOne_T3);
    net.addArcFromPlaceToTransition(_P3, qThree_splitOne_T1);
    net.addArcFromPlaceToTransition(_P67, qThree_splitOne_T2);
    net.addArcFromPlaceToTransition(_P65, qThree_splitOne_T2);
    net.addArcFromPlaceToTransition(_P4, qThree_splitOne_T0);
    net.addArcFromPlaceToTransition(_P96, qThree_iLaneOne_oT3);
    net.addArcFromPlaceToTransition(_P28, qThree_splitOne_T7);
    net.addArcFromPlaceToTransition(_P115, qThree_splitOne_T7);
    net.addArcFromPlaceToTransition(_P115, qThree_splitOne_T3);
    net.addArcFromPlaceToTransition(_P63, qThree_splitOne_T3);
    net.addArcFromPlaceToTransition(_P30, qThree_splitOne_T4);
    net.addArcFromPlaceToTransition(_P58, qThree_splitOne_T4);
    net.addArcFromPlaceToTransition(_P33, qFour_T3);
    net.addArcFromPlaceToTransition(_P68, qFour_T4);
    net.addArcFromPlaceToTransition(_P35, qFour_T1);
    net.addArcFromPlaceToTransition(_P34, qFour_T2);
    net.addArcFromPlaceToTransition(_P36, qFour_T0);
    net.addArcFromPlaceToTransition(_P71, qOne_iLaneOne_T2);
    net.addArcFromPlaceToTransition(_P119, qOne_iLaneOne_T2);
    net.addArcFromPlaceToTransition(_P71, qOne_iLaneOne_T0);
    net.addArcFromPlaceToTransition(_P117, qOne_iLaneOne_T0);
    net.addArcFromPlaceToTransition(_P15, qFour_splitOne_T0);
    net.addArcFromPlaceToTransition(_P120, qOne_iLaneOne_T1);
    net.addArcFromPlaceToTransition(_P71, qOne_iLaneOne_T1);
    net.addArcFromPlaceToTransition(_P12, qThree_T0);
    net.addArcFromPlaceToTransition(_P14, qThree_T1);
    net.addArcFromPlaceToTransition(_P19, qThree_T2);
    net.addArcFromPlaceToTransition(_P21, qThree_T3);
    net.addArcFromPlaceToTransition(_P77, qThree_T4);
    net.addArcFromPlaceToTransition(_P56, qTwo_calcOne_oT14);
    net.addArcFromPlaceToTransition(_P85, qFour_splitOne_T7);
    net.addArcFromPlaceToTransition(_P100, qFour_splitOne_T7);
    net.addArcFromPlaceToTransition(_P87, qFour_splitOne_T4);
    net.addArcFromPlaceToTransition(_P91, qFour_splitOne_T4);
    net.addArcFromPlaceToTransition(_P100, qFour_splitOne_T3);
    net.addArcFromPlaceToTransition(_P78, qFour_splitOne_T3);
    net.addArcFromPlaceToTransition(_P79, qFour_splitOne_T2);
    net.addArcFromPlaceToTransition(_P76, qFour_splitOne_T2);
    net.addArcFromPlaceToTransition(_P13, qFour_splitOne_T1);
    net.addArcFromPlaceToTransition(P33, oT2);
    net.addArcFromPlaceToTransition(P32, oT1);
    net.addArcFromPlaceToTransition(P31, oT4);
    net.addArcFromPlaceToTransition(P30, oT3);
    net.addArcFromPlaceToTransition(_P57, qTwo_calcOne_oT13);
    net.addArcFromTransitionToPlace(qThree_iLaneOne_T2, _P99);
    net.addArcFromTransitionToPlace(qFour_calcOne_oT9, _P128);
    net.addArcFromTransitionToPlace(qThree_splitOne_oT6, _P81);
    net.addArcFromTransitionToPlace(qThree_splitOne_oT5, _P80);
    net.addArcFromTransitionToPlace(qOne_calcOne_T2, _P102);
    net.addArcFromTransitionToPlace(qOne_calcOne_T3, _P101);
    net.addArcFromTransitionToPlace(qOne_calcOne_T8, _P112);
    net.addArcFromTransitionToPlace(qOne_calcOne_T8, _P29);
    net.addArcFromTransitionToPlace(qTwo_iLaneOne_oT3, _P116);
    net.addArcFromTransitionToPlace(qOne_calcOne_T7, _P114);
    net.addArcFromTransitionToPlace(qOne_calcOne_T7, _P111);
    net.addArcFromTransitionToPlace(qOne_oT3, P40);
    net.addArcFromTransitionToPlace(qThree_oT2, P23);
    net.addArcFromTransitionToPlace(qOne_oT2, P27);
    net.addArcFromTransitionToPlace(qThree_oT3, P42);
    net.addArcFromTransitionToPlace(qOne_oT1, P20);
    net.addArcFromTransitionToPlace(qThree_iLaneOne_T1, _P99);
    net.addArcFromTransitionToPlace(qThree_oT1, P24);
    net.addArcFromTransitionToPlace(qThree_iLaneOne_T0, _P99);
    net.addArcFromTransitionToPlace(qThree_iLaneOne_T0, _P96);
    net.addArcFromTransitionToPlace(qOne_calcOne_T0, _P102);
    net.addArcFromTransitionToPlace(qOne_calcOne_T1, _P103);
    net.addArcFromTransitionToPlace(qOne_calcOne_T1, _P113);
    net.addArcFromTransitionToPlace(qOne_calcOne_T10, _P31);
    net.addArcFromTransitionToPlace(qOne_calcOne_T10, _P22);
    net.addArcFromTransitionToPlace(qTwo_oT1, P22);
    net.addArcFromTransitionToPlace(qThree_calcOne_oT9, _P153);
    net.addArcFromTransitionToPlace(qFour_splitOne_oT5, _P66);
    net.addArcFromTransitionToPlace(qFour_splitOne_oT6, _P59);
    net.addArcFromTransitionToPlace(qOne_splitOne_T2, _P51);
    net.addArcFromTransitionToPlace(qOne_splitOne_T1, _P53);
    net.addArcFromTransitionToPlace(qOne_splitOne_T1, _P52);
    net.addArcFromTransitionToPlace(qOne_splitOne_T0, _P55);
    net.addArcFromTransitionToPlace(qOne_splitOne_T0, _P54);
    net.addArcFromTransitionToPlace(qFour_oT3, P43);
    net.addArcFromTransitionToPlace(qFour_oT2, P25);
    net.addArcFromTransitionToPlace(qFour_oT1, P26);
    net.addArcFromTransitionToPlace(qFour_iLaneOne_T2, _P121);
    net.addArcFromTransitionToPlace(qFour_iLaneOne_T1, _P121);
    net.addArcFromTransitionToPlace(qFour_iLaneOne_T0, _P121);
    net.addArcFromTransitionToPlace(qFour_iLaneOne_T0, _P110);
    net.addArcFromTransitionToPlace(qThree_calcOne_T10, _P39);
    net.addArcFromTransitionToPlace(qThree_calcOne_T10, _P47);
    net.addArcFromTransitionToPlace(qOne_T0, _P134);
    net.addArcFromTransitionToPlace(qFour_calcOne_T3, _P140);
    net.addArcFromTransitionToPlace(qFour_calcOne_T2, _P138);
    net.addArcFromTransitionToPlace(qOne_T2, _P120);
    net.addArcFromTransitionToPlace(qFour_calcOne_T1, _P136);
    net.addArcFromTransitionToPlace(qFour_calcOne_T1, _P142);
    net.addArcFromTransitionToPlace(qOne_T1, _P131);
    net.addArcFromTransitionToPlace(qFour_calcOne_T0, _P138);
    net.addArcFromTransitionToPlace(T0, _P150);
    net.addArcFromTransitionToPlace(T0, _P12);
    net.addArcFromTransitionToPlace(qOne_T4, _P119);
    net.addArcFromTransitionToPlace(qOne_T4, _P84);
    net.addArcFromTransitionToPlace(qFour_calcOne_T7, _P141);
    net.addArcFromTransitionToPlace(qFour_calcOne_T7, _P144);
    net.addArcFromTransitionToPlace(T1, _P148);
    net.addArcFromTransitionToPlace(T1, _P14);
    net.addArcFromTransitionToPlace(qOne_T3, _P83);
    net.addArcFromTransitionToPlace(T2, _P25);
    net.addArcFromTransitionToPlace(T2, _P36);
    net.addArcFromTransitionToPlace(T3, _P27);
    net.addArcFromTransitionToPlace(T3, _P35);
    net.addArcFromTransitionToPlace(T4, _P17);
    net.addArcFromTransitionToPlace(T5, _P24);
    net.addArcFromTransitionToPlace(T6, _P21);
    net.addArcFromTransitionToPlace(T7, _P33);
    net.addArcFromTransitionToPlace(qFour_calcOne_T8, _P143);
    net.addArcFromTransitionToPlace(qFour_calcOne_T8, _P69);
    net.addArcFromTransitionToPlace(T8, _P20);
    net.addArcFromTransitionToPlace(T9, _P23);
    net.addArcFromTransitionToPlace(qTwo_calcOne_T10, _P57);
    net.addArcFromTransitionToPlace(qTwo_calcOne_T10, _P56);
    net.addArcFromTransitionToPlace(qFour_calcOne_T10, _P62);
    net.addArcFromTransitionToPlace(qFour_calcOne_T10, _P64);
    net.addArcFromTransitionToPlace(qOne_calcOne_oT14, _P130);
    net.addArcFromTransitionToPlace(qTwo_oT2, P21);
    net.addArcFromTransitionToPlace(qOne_calcOne_oT13, _P126);
    net.addArcFromTransitionToPlace(qTwo_oT3, P41);
    net.addArcFromTransitionToPlace(qOne_splitOne_oT5, _P129);
    net.addArcFromTransitionToPlace(qTwo_calcOne_T8, _P156);
    net.addArcFromTransitionToPlace(qTwo_calcOne_T8, _P75);
    net.addArcFromTransitionToPlace(T10, _P19);
    net.addArcFromTransitionToPlace(qOne_splitOne_oT6, _P132);
    net.addArcFromTransitionToPlace(qTwo_iLaneOne_T0, _P163);
    net.addArcFromTransitionToPlace(qTwo_iLaneOne_T0, _P171);
    net.addArcFromTransitionToPlace(T11, _P34);
    net.addArcFromTransitionToPlace(qTwo_iLaneOne_T1, _P163);
    net.addArcFromTransitionToPlace(qTwo_iLaneOne_T2, _P163);
    net.addArcFromTransitionToPlace(qTwo_calcOne_T7, _P146);
    net.addArcFromTransitionToPlace(qTwo_calcOne_T7, _P157);
    net.addArcFromTransitionToPlace(qThree_calcOne_T1, _P160);
    net.addArcFromTransitionToPlace(qThree_calcOne_T1, _P164);
    net.addArcFromTransitionToPlace(qThree_calcOne_T0, _P158);
    net.addArcFromTransitionToPlace(qThree_calcOne_T3, _P159);
    net.addArcFromTransitionToPlace(qThree_calcOne_T2, _P158);
    net.addArcFromTransitionToPlace(qThree_calcOne_T7, _P162);
    net.addArcFromTransitionToPlace(qThree_calcOne_T7, _P168);
    net.addArcFromTransitionToPlace(qThree_calcOne_T8, _P166);
    net.addArcFromTransitionToPlace(qThree_calcOne_T8, _P41);
    net.addArcFromTransitionToPlace(qTwo_calcOne_T1, _P152);
    net.addArcFromTransitionToPlace(qTwo_calcOne_T1, _P147);
    net.addArcFromTransitionToPlace(qTwo_calcOne_T0, _P149);
    net.addArcFromTransitionToPlace(qTwo_calcOne_T3, _P151);
    net.addArcFromTransitionToPlace(qTwo_calcOne_T2, _P149);
    net.addArcFromTransitionToPlace(T21, P31);
    net.addArcFromTransitionToPlace(T20, P30);
    net.addArcFromTransitionToPlace(T23, P33);
    net.addArcFromTransitionToPlace(T22, P32);
    net.addArcFromTransitionToPlace(qTwo_T1, _P133);
    net.addArcFromTransitionToPlace(qTwo_T2, _P10);
    net.addArcFromTransitionToPlace(qTwo_T3, _P105);
    net.addArcFromTransitionToPlace(qTwo_T4, _P11);
    net.addArcFromTransitionToPlace(qTwo_T4, _P104);
    net.addArcFromTransitionToPlace(qTwo_T0, _P135);
    net.addArcFromTransitionToPlace(qTwo_splitOne_oT6, _P6);
    net.addArcFromTransitionToPlace(qTwo_splitOne_oT5, _P5);
    net.addArcFromTransitionToPlace(qFour_calcOne_oT14, _P61);
    net.addArcFromTransitionToPlace(qFour_calcOne_oT13, _P68);
    net.addArcFromTransitionToPlace(qTwo_calcOne_oT9, _P16);
    net.addArcFromTransitionToPlace(qOne_iLaneOne_oT3, _P18);
    net.addArcFromTransitionToPlace(qOne_calcOne_oT9, _P117);
    net.addArcFromTransitionToPlace(qFour_iLaneOne_oT3, _P74);
    net.addArcFromTransitionToPlace(qTwo_splitOne_T7, _P37);
    net.addArcFromTransitionToPlace(qTwo_splitOne_T7, _P118);
    net.addArcFromTransitionToPlace(qTwo_splitOne_T0, _P38);
    net.addArcFromTransitionToPlace(qTwo_splitOne_T0, _P42);
    net.addArcFromTransitionToPlace(qOne_splitOne_T7, _P127);
    net.addArcFromTransitionToPlace(qOne_splitOne_T7, _P44);
    net.addArcFromTransitionToPlace(qThree_calcOne_oT13, _P77);
    net.addArcFromTransitionToPlace(qTwo_splitOne_T2, _P43);
    net.addArcFromTransitionToPlace(qTwo_splitOne_T1, _P40);
    net.addArcFromTransitionToPlace(qTwo_splitOne_T1, _P45);
    net.addArcFromTransitionToPlace(qOne_splitOne_T4, _P46);
    net.addArcFromTransitionToPlace(qTwo_splitOne_T4, _P48);
    net.addArcFromTransitionToPlace(qOne_splitOne_T3, _P49);
    net.addArcFromTransitionToPlace(qThree_calcOne_oT14, _P82);
    net.addArcFromTransitionToPlace(qTwo_splitOne_T3, _P50);
    net.addArcFromTransitionToPlace(qThree_splitOne_T1, _P65);
    net.addArcFromTransitionToPlace(qThree_splitOne_T1, _P58);
    net.addArcFromTransitionToPlace(qThree_splitOne_T2, _P60);
    net.addArcFromTransitionToPlace(qThree_splitOne_T0, _P67);
    net.addArcFromTransitionToPlace(qThree_splitOne_T0, _P63);
    net.addArcFromTransitionToPlace(qThree_iLaneOne_oT3, _P8);
    net.addArcFromTransitionToPlace(qThree_splitOne_T7, _P115);
    net.addArcFromTransitionToPlace(qThree_splitOne_T7, _P28);
    net.addArcFromTransitionToPlace(qThree_splitOne_T3, _P30);
    net.addArcFromTransitionToPlace(qThree_splitOne_T4, _P32);
    net.addArcFromTransitionToPlace(qFour_T3, _P15);
    net.addArcFromTransitionToPlace(qFour_T4, _P139);
    net.addArcFromTransitionToPlace(qFour_T4, _P13);
    net.addArcFromTransitionToPlace(qFour_T1, _P175);
    net.addArcFromTransitionToPlace(qFour_T2, _P137);
    net.addArcFromTransitionToPlace(qFour_T0, _P170);
    net.addArcFromTransitionToPlace(qOne_iLaneOne_T2, _P71);
    net.addArcFromTransitionToPlace(qOne_iLaneOne_T0, _P71);
    net.addArcFromTransitionToPlace(qOne_iLaneOne_T0, _P70);
    net.addArcFromTransitionToPlace(qFour_splitOne_T0, _P79);
    net.addArcFromTransitionToPlace(qFour_splitOne_T0, _P78);
    net.addArcFromTransitionToPlace(qOne_iLaneOne_T1, _P71);
    net.addArcFromTransitionToPlace(qThree_T0, _P145);
    net.addArcFromTransitionToPlace(qThree_T1, _P26);
    net.addArcFromTransitionToPlace(qThree_T2, _P154);
    net.addArcFromTransitionToPlace(qThree_T3, _P4);
    net.addArcFromTransitionToPlace(qThree_T4, _P155);
    net.addArcFromTransitionToPlace(qThree_T4, _P3);
    net.addArcFromTransitionToPlace(qTwo_calcOne_oT14, _P7);
    net.addArcFromTransitionToPlace(qFour_splitOne_T7, _P100);
    net.addArcFromTransitionToPlace(qFour_splitOne_T7, _P85);
    net.addArcFromTransitionToPlace(qFour_splitOne_T4, _P86);
    net.addArcFromTransitionToPlace(qFour_splitOne_T3, _P87);
    net.addArcFromTransitionToPlace(qFour_splitOne_T2, _P89);
    net.addArcFromTransitionToPlace(qFour_splitOne_T1, _P76);
    net.addArcFromTransitionToPlace(qFour_splitOne_T1, _P91);
    net.addArcFromTransitionToPlace(qTwo_calcOne_oT13, _P9);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(_P28, new UnifiedToken(1.0));
    net.setInitialMarkingForPlace(_P44, new UnifiedToken(1.0));
    net.setInitialMarkingForPlace(_P71, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(_P85, new UnifiedToken(1.0));
    net.setInitialMarkingForPlace(_P99, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(_P101, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(_P111, new UnifiedToken(20.0));
    net.setInitialMarkingForPlace(_P118, new UnifiedToken(1.0));
    net.setInitialMarkingForPlace(_P121, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(_P140, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(_P144, new UnifiedToken(20.0));
    net.setInitialMarkingForPlace(_P151, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(_P157, new UnifiedToken(20.0));
    net.setInitialMarkingForPlace(_P159, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(_P163, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(_P168, new UnifiedToken(20.0));
  }

}