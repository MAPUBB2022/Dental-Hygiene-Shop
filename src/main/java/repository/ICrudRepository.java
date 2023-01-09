package repository;

public interface ICrudRepository<ID, E> {
    void add(E e);

    void delete(ID id);


    E findById(ID id);
}
