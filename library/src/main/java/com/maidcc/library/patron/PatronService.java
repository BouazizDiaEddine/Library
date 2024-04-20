package com.maidcc.library.patron;

import com.maidcc.library.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    private final PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository=patronRepository;
    }

    @Transactional(readOnly = true)
    List<Patron> getAllPatrons(){
        return patronRepository.findAll();
    }

    @Transactional(readOnly = true)
    Optional<Patron> getPatron(Long id){
        return patronRepository.findById(id);
    }



    public Patron savePatron(Patron patron) {
        try {
            return patronRepository.save(patron);
        } catch (
                DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Invalid data: " + e.getMessage());
        }
    }


    public Patron updatePatron(long id, Patron patron) {
        try {
            Optional<Patron> optionalUpdatePatron = patronRepository.findById(id);
            if(optionalUpdatePatron.isPresent()) {
                Patron updatePatron = optionalUpdatePatron.get();
                updatePatron.setAddress(patron.getFullName());
                updatePatron.setMail(patron.getMail());
                updatePatron.setPhone(patron.getPhone());
                updatePatron.setFullName(patron.getFullName());
                patronRepository.save(updatePatron);
                return updatePatron;
            }else throw new IllegalArgumentException("Patron with ID " + id + " not found");
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Invalid data: " + e.getMessage());
        }
    }


    public void deletePatron(Long id) {
        if(!patronRepository.existsById(id))
            throw new IllegalArgumentException("Patron with id " + id + " not found");
        patronRepository.deleteById(id);
    }

}
