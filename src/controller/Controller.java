package controller;

import model.ProgramState;
import model.adts.*;
import model.exceptions.EvaluationException;
import model.values.IValue;
import model.values.ReferenceValue;
import repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller implements IController {
    private final IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void oneStepForAllPrograms(List<ProgramState> programList) {
        // First log the list of program states
        for (ProgramState program : programList) {
            repository.logProgramState(program);
        }
        // Prepare the list of callables for concurrency
        List<Callable<ProgramState>> callList = programList.stream()
                .map((ProgramState program) -> (Callable<ProgramState>) program::oneStep).toList();
        // Start the exec of callables
        try {
            // This list contains the newly created threads from the already running programs
            List<ProgramState> newProgramList = executor.invokeAll(callList).stream().map(future -> {
                try {
                    return future.get();
                } catch (java.util.concurrent.ExecutionException | InterruptedException exception) {
                    repository.logErrorMessage(exception.getMessage());
                }
                return null;
            }).filter(Objects::nonNull).toList();
            // Add all the new programs to the programs list
            programList.addAll(newProgramList);
            // After they have been executed and garbage collected, log them
            programList.forEach(program -> program.setHeap(garbageCollector(program.getSymbolTable(), program.getHeap())));
            programList.forEach(repository::logProgramState);
            // Now save them to the repository
            repository.setProgramList(programList);
        } catch (InterruptedException e) {
            repository.logErrorMessage(e.getMessage());
        }
    }

    @Override
    public void allStep() {
        // Call the factory of executors
        executor = Executors.newFixedThreadPool(2);
        // Remove completed programs
        List<ProgramState> programs = removeCompletedPrograms(repository.getProgramList());
        while (!programs.isEmpty()) {
            oneStepForAllPrograms(programs);
            // Remove the completed programs after each step
            programs = removeCompletedPrograms(repository.getProgramList());
        }
        executor.shutdownNow();
        // Update repository state
        repository.setProgramList(programs);
    }

    public IMyHeap garbageCollector(SymbolTable symbolTable, IMyHeap heap) {
        /*

         */
        // Create a list with correct addresses from the symbol table and the heap
        List<Integer> addressesToKeep = Stream.concat(getAddresses(symbolTable.values()).stream(),
                getAddresses(heap.getContent().values()).stream()).toList();
        IMyHeap newHeap = new MyHeap();
        newHeap.setContent(heap.getContent().entrySet().stream().filter(elem -> addressesToKeep.contains(elem.getKey())).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        return newHeap;
    }

    private List<Integer> getAddresses(Collection<IValue> collection) {
        /*
            returns all the addresses from the elements that are Reference Values from a collection
         */
        return collection.stream().filter(elem -> elem instanceof ReferenceValue).map(elem -> ((ReferenceValue) elem).getAddress()).collect(Collectors.toList());
    }

    @Override
    public int repositorySize() {
        return repository.size();
    }

    @Override
    public List<ProgramState> getAll() {
        return repository.getProgramList();
    }

    @Override
    public void resetProgram() {
        repository.resetAllPrograms();
    }

    @Override
    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programList) {
        return programList.stream().filter(program -> !program.isCompleted()).collect(Collectors.toList());
    }

    @Override
    public ProgramState getProgramById(int id) {
        for (ProgramState prg : repository.getProgramList()){
            if (prg.getId() == id)
                return prg;
        }
        return null;
    }

    @Override
    public void oneStep() {
        // Call the factory of executors
        executor = Executors.newFixedThreadPool(2);
        // Remove completed programs
        List<ProgramState> programs = removeCompletedPrograms(repository.getProgramList());
        oneStepForAllPrograms(programs);
        // Remove the completed programs after each step
        programs = removeCompletedPrograms(repository.getProgramList());
        executor.shutdownNow();
        // Update repository state
        repository.setProgramList(programs);
    }
}
