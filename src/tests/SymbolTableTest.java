package tests;

import model.adts.SymbolTable;
import model.values.BoolValue;
import model.values.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SymbolTableTest {
    @Test
    public void deepCopyTest() {
        SymbolTable original = new SymbolTable();
        SymbolTable copy;
        original.put("v", new IntValue(2));
        copy = original.deepCopy();
        original.put("a", new IntValue(3));
        original.put("v", new BoolValue(true));
        assertNotEquals(original.get("v"), copy.get("v"));
        assertNotEquals(original.get("a"), copy.get("a"));
    }
}
