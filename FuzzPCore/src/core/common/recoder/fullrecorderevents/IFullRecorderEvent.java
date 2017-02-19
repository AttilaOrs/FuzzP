package core.common.recoder.fullrecorderevents;

public interface IFullRecorderEvent {

  String makeString();

  void buildFromString(String str);

  boolean isMyString(String str);

}
