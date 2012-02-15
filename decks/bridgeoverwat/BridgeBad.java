package org.netmelody.sandpit.wat;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public final class BridgeBad {
    
    public interface Taster<T> {
        boolean tasty(T item);
    }
    
    public interface StrTaster extends Taster<String> {
        @Override
        boolean tasty(String input);
    }
    
    public class Shop<T> {
        Taster<T> taster;
        Shop(Taster<T> t) { this.taster = t; }
        
        boolean willBuy(T item) {
            return taster.tasty(item);
        }
    }
    
    @Test public void canEvaluate() {
        Mockery context = new Mockery();
        
        final StrTaster bob = context.mock(StrTaster.class, "Bob");
        Shop<String> asda = new Shop<String>(bob);
        
        context.checking(new Expectations() {{
            allowing(bob).tasty("soup"); will(returnValue(true));
        }});
        
        assertTrue(asda.willBuy("soup"));
    }

}
