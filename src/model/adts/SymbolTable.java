package model.adts;

import model.values.IValue;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SymbolTable {
    private ConcurrentHashMap<String, IValue> map = new ConcurrentHashMap<String, IValue>();

    public void put(String key, IValue value) {
        map.put(key, value);
    }

    public IValue get(String key) {
        return map.get(key);
    }

    public IValue remove(String key) {
        return map.remove(key);
    }

    public Collection<IValue> values() {
        return map.values();
    }

    public Set<String> keys() {
        return map.keySet();
    }

    public ConcurrentHashMap<String, IValue> getContent() {
        return map;
    }

    public void setContent(ConcurrentHashMap<String, IValue> newContent) {
        map = newContent;
    }

    public SymbolTable deepCopy() {
        SymbolTable copy = new SymbolTable();
        ConcurrentHashMap<String, IValue> content = new ConcurrentHashMap<>();
        for (Map.Entry<String, IValue> entry : map.entrySet()) {
            content.put(entry.getKey(), entry.getValue().deepCopy());
        }
        copy.setContent(content);
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String key : map.keySet())
            result.append(key).append(" => ").append(map.get(key).toString()).append('\n');
        return result.toString();
    }
}
