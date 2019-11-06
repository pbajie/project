package shop.command;
import java.util.Stack;

final class CommandHistoryObj implements CommandHistory {
  Stack<UndoableCommand> _undoStack = new Stack<UndoableCommand>();
  Stack<UndoableCommand> _redoStack = new Stack<UndoableCommand>();
  RerunnableCommand _undoCmd = new RerunnableCommand () {
      public boolean run () {
        boolean result = !_undoStack.empty(); 
        if (result) {
          // Undo
          // TODO  
          UndoableCommand ur = _undoStack.pop();
          _redoStack.push(ur).undo();  
        }
        return result;
      }
    };
  RerunnableCommand _redoCmd = new RerunnableCommand () {
      public boolean run () {
        boolean result = !_redoStack.empty(); 
        if (result) {                           
          
          // TODO 
        UndoableCommand dr = _redoStack.pop();
        _undoStack.push(dr).redo();
        }
        return result;
      }
    };

  public void add(UndoableCommand cmd) {
    // TODO  //add undo and clear redo 
	  _undoStack.add(cmd);
	  //if(!_redoStack.empty())
	   _redoStack.clear();  
	
  }
  
  public RerunnableCommand getUndo() {
    return _undoCmd;
  }
  
  public RerunnableCommand getRedo() {
    return _redoCmd;
  }
  
  // For testing
  UndoableCommand topUndoCommand() {
    if (_undoStack.empty())
      return null;
    else//Looks at the object at the top of this stack without removing it from the stack.
      return _undoStack.peek();
  }
  // For testing
  UndoableCommand topRedoCommand() {
    if (_redoStack.empty())
      return null;
    else
      return _redoStack.peek();
  }
}
