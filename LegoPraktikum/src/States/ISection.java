package States;

public interface ISection {
  
  public boolean running = true;


  String getName();
  
  void onStart();
  
  void onEnd();
  
  void setRunningState(boolean state);
  
}
