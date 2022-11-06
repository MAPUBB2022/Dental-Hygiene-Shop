package model.repository;

public interface ICrudRepository<ID, E> {
    void add(E e); //fuge ein Element von Typ E hinzu

    void delete(ID id); // losche nach ID


    E findById(ID id); // suche nach ID
}
