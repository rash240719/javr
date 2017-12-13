import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.SEXP;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.FileNotFoundException;

public class RIntermediary extends R {
    protected SEXP operate(String operation, Object[] values) {
        SEXP result = null;

        try {
            result = (SEXP) super.executeScript(operation, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
