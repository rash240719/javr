import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.SEXP;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.FileNotFoundException;

public class R {
    protected SEXP executeScript(String script, Object[] args) {
        RenjinScriptEngineFactory factory = new RenjinScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine();
        String dir = System.getProperty("user.dir") + "/src/main/resources/";

        try {
            int i = 0;

            if (args != null) {
                for (Object o : args) {
                    engine.put("val" + i, o);
                    i++;
                }
            }

            return (SEXP) engine.eval(new java.io.FileReader(dir + script + ".r"));
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
