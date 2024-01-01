package model.statements;

import model.ProgramState;
import model.adts.IMyDictionary;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.types.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException;

    IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException;

    IStatement deepCopy();
}
