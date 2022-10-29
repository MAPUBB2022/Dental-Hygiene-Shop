package model.repository;

public interface ICrudRepository<ID, E> {
    void add(E e); //fuge ein Element von Typ E hinzu

    void delete(ID id); // losche nach ID

    void update(ID id, E e); // erstelle update des Elements mit einem neuen Element e

    E findById(ID id); // suche nach ID
}
