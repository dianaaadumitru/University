package ro.ubb.lab11.core.repository;


import ro.ubb.lab11.core.model.Spell;

import java.util.List;

public interface ISpellRepository extends IRepository<Spell, Long>{
    List<Spell> findAllByName(String name);

}
