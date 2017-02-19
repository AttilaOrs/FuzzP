package core.common.recoder;

public interface FullRecordable<SELF extends FullRecordable<SELF>> {

  String shortString();

  SELF myClone();

  SELF unite(SELF f);

  boolean isPhi();

}
