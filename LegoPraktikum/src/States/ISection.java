package States;

public interface ISection {
  
  boolean running = true;

  String getName();
  
  void onStart();
  
  void onEnd();
  
  void setRunningState(boolean state);
  
}
