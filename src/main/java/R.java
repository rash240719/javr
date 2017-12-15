import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.SEXP;

import javax.script.ScriptEngine;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class R {
    protected SEXP executeScript(String script, Object[] args) {
        RenjinScriptEngineFactory factory = new RenjinScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine();
        StringBuilder sb = new StringBuilder();
        String str = "";
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(script + ".r");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            int i = 0;
            if (args != null) {
                for (Object o : args) {
                    engine.put("val" + i, o);
                    i++;
                }
            }

            while ((str = bufferedReader.readLine()) != null) {
                sb.append(str);
            }

            return (SEXP) engine.eval(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
