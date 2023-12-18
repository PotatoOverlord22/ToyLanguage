package model.expressions;

import model.adts.IMyDictionary;
import model.adts.IMyHeap;
import model.adts.SymbolTable;
import model.exceptions.EvaluationException;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapReadExpression implements IExpression{
    // TODO: write tests
    private final IExpression expression;

    public HeapReadExpression(IExpression expression){
        this.expression = expression;
    }

    @Override
    public IValue evaluate(SymbolTable table, IMyHeap heap) throws EvaluationException {
        // Check if the value is of reference value
        IValue expValue = expression.evaluate(table, heap);
        if (!(expValue instanceof ReferenceValue refValue))
            throw new EvaluationException("Expression " + expression + " is not of type reference value");
        // Check if the address is an allocated address in the heap
        if (heap.get(refValue.getAddress()) == null)
            throw new EvaluationException("The address " + refValue.getAddress() + " is not in the heap");
        // Return the value from the specified heap address
        return heap.get(refValue.getAddress());
    }

    @Override
    public IExpression deepCopy() {
        return new HeapReadExpression(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "heapRead(" + expression.toString() + ")";
    }
}
