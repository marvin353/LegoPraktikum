package States;

public interface ISection extends Runnable{
  
  //public boolean running = true;


  String getName();
  
  void onStart();
  
  void onEnd();
  
  void setRunningState(boolean state);
  
}
