package myTest;

import myAdapter.HIterator;
import myAdapter.HList;
import myAdapter.HListIterator;
import myAdapter.ListAdapter;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Summary:
 * <br>Questa classe testa tutti i metodi della classe ListIteratorAdapter.
 * <br><br>
 * Design test:
 * <br>In questa classe ogni test punta a verificare il corretto funzionamento per ogni singolo metodo.
 * <br><br>
 * Description:
 * <br>La classe ListIteratorAdapter implementa HIterator and HListIterator.
 * Per semplicità, i valori inseriti sono tutti interi appartenenti alla classe
 * Integer (che è comunque parte della classe Object, pertanto rispetta il tipo dei parametri da inserire).
 * <br><br>
 * Preconditions:
 * <br>Un nuovo oggetto vuoto di tipo ListIteratorAdapter deve sempre essere creato prima di ogni test.
 * <br>Le variabili vengono sempre inizializzate, a meno che non voglia verificare per il caso in cui siano uguali a null.
 * <br>Tutti gli elementi contenuti nella lista sono conosciuti a priori, così da poter verificare il corretto funzionamento dei metodi.
 * <br><br>
 * Postconditions:
 * <br>I metodi implementati modificano la lista nel modo atteso: gli elementi contenuti sono esattamente quelli previsti.
 * <br><br>
 * Execution record:
 * <br>Ogni metodo testato è corretto se tutti i test che verificano il corretto funzionamento hanno un risultato positivo.
 * La corretta esecuzione dell'intero test può essere considerato come record di esecuzione.
 * <br><br>
 * Execution variables:
 * <br>HList list - lista vuota utilizzata per i metodi comuni sia a HListIterator che HIterator
 * <br>HList listWithData - lista non vuota con cui vengono testati i metodi forniti dall'interfaccia HListIterator
 * <br>HIterator it - iteratore con cui vengono testati i metodi forniti dall'interfaccia HIterator
 * <br>HListIterator lit - listIterator vuoto con cui vengono testati i metodi forniti dall'interfaccia HListIterator
 * <br><br>
 *
 */


public class ListIteratorAdapterTest {

    HIterator it;
    HListIterator lit;
    private HList list, listWithData;

    /**
     * Summary:
     * <br>Metodo per inizializzare le variabili prima dei test.
     * <br><br>
     * Description:
     * <br>Viene creata una nuova lista vuota e una non vuota prima di ogni metodo di test,
     * in questo modo la list su cui vengono invocati i vari metodi testati ha sempre uno stato valido.
     *  <br><br>
     */
    @Before
    public void setup() {
        list = new ListAdapter();
        listWithData = new ListAdapter();

        for (int i = 0; i < 5; i++)
            listWithData.add(i + 1);
    }

    /**
     * Summary:
     * <br>Test del costruttore senza parametri.
     * <br><br>
     * Design test:
     * <br>Tentativo di creazione di un listIterator da lista vuota e da lista con elementi.
     * <br><br>
     * Description:
     * <br>Crea iteratore da lista vuota e da lista piena e ne controlla la posizione iniziale.
     * <br><br>
     * Preconditions:
     * <br>Il metodo nextIndex() è implementato correttamente.
     * <br><br>
     * Postconditions:
     * <br>L'iteratore ha posizione 0 per lista vuota, 1 per lista piena.
     * <br><br>
     * Expected results:
     * <br>L'indice dell'iteratore uguale a 0 per lista vuota, 1 altrimenti.
     *  <br><br>
     */
    @Test
    public void testConstructor() {
        lit = list.listIterator();
        assertEquals(0,lit.nextIndex());

        lit = listWithData.listIterator();
        assertEquals(1,lit.nextIndex());

    }

    /**
     * Summary:
     * <br> Test del costruttore con parametri
     * <br><br>
     * Design test:
     * <br>Tentativo di creazione di un listIterator da lista vuota e indice non valido, e da lista con elementi e indice valido.
     * <br><br>
     * Description:
     * <br>Crea l'iteratore da lista vuota e da lista piena e ne controlla la posizione iniziale.
     * <br><br>
     * Preconditions:
     * <br> Il metodo nextIndex() è implementato correttamente.
     * <br><br>
     * Postconditions:
     * <br>L'iteratore è situato nella posizione passata come parametro.
     * <br><br>
     * Expected results:
     * <br>L'indice dell'iteratore uguale alla posizione specificata, se valida. Altrimenti viene lanciata l'eccezione.
     *  <br><br>
     */
    @Test
    public void testConstructorWithParameters() {

        try{
            lit = list.listIterator(1);
            throw new Exception();
        }catch (Exception e){
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }
        lit = listWithData.listIterator(2);
        assertEquals(3,lit.nextIndex());

    }

    /**
     * Summary:
     * <br>Verifica che il metodo indichi correttamente se l'iteratore è alla fine della lista o meno.
     * <br><br>
     * Design test:
     * <br>Il metodo viene invocato su un iteratore di lista vuota e di una lista non vuota. Viene poi invocato su un listIterator di una lista piena e di una lista vuota. Esso viene poi invocato su listIterator che punta a una locazione centrale della lista e su uno che punta alla fine della lista.
     * <br><br>
     * Description:
     * <br>In generale, il <metodo viene invocato su una lista e viene verificato il valore ritornato dall'invocazione sull'iteratore del metodo hasNext(). Ciò viene svolto nel caso di un iteratore di una lista vuota e di lista non vuota. Analogamente con un listIterator. Viene infine verificato il caso in cui l'iteratore non punti all'inizio della lista, ma alla fine e il caso in cui punti a una locazione interna alla lista.
     * <br><br>
     * Preconditions:
     * <br>La lista può essere vuota.
     * <br><br>
     * Postconditions:
     * <br>Nel caso in cui la lista sia vuota o l'iteratore sia alla fine della lista hasNext() restituisce false, altrimenti true.
     * <br><br>
     * Expected results:
     * <br>True nel caso in cui vi siano elementi successivi all'iteratore, altrimenti false.
     * <br><br>
     */
    @Test
    public void testHasNext() {

        it = list.iterator();
        assertFalse(it.hasNext());

        it = listWithData.listIterator();
        assertTrue(it.hasNext());

        it = listWithData.listIterator(2);
        assertTrue(it.hasNext());

        it = listWithData.listIterator(5);
        assertFalse(it.hasNext());

    }

    /**
     * Summary:
     * <br> Verifica che il metodo restituisca l'elemento che succede l'iteratore.
     * <br><br>
     * Design test:
     * <br>Il metodo viene invocato su un iteratore di una lista vuota e di una lista piena. Viene poi invocato su un listIterator di una lista vuota e di una lista piena.
     * <br><br>
     * Description:
     * <br>Il metodo viene invocato su un iteratore di una lista vuota e viene verificato il corretto lancio dell'eccezione. Il metodo viene poi invocato su un iteratore di una lista non vuota e viene verificato che il valore restituito dal metodo sia corretto. Analogamente viene svolto con un listIterator.
     * <br><br>
     * Preconditions:
     * <br>La lista può anche essere vuota.
     * <br><br>
     * Postconditions:
     * <br>Il metodo restituisce l'elemento che succede quello puntato dall'iteratore. Nel caso in cui la lista è vuota, lancia correttamente l'eccezione.
     * <br><br>
     * Expected results:
     * <br>Lancio dell'eccezione nel caso di lista vuota. Nel caso di lista piena, invece, l'elemento della lista che succede l'iteratore.
     * <br><br>
     */
    @Test
    public void testNext() {
        it = list.iterator();
        try{
            it.next();
        }catch (Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }

        it = listWithData.iterator();
        assertEquals(1, it.next());

        lit = list.listIterator();
        try{
            lit.next();
            throw new Exception();
        }catch (Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }

        lit = listWithData.listIterator();
        assertEquals(1, lit.next());
        assertEquals(2, lit.next());
        lit = listWithData.listIterator(2);
        assertEquals(3, lit.next());
        lit = listWithData.listIterator(5);
        try{
            lit.next();
            throw new Exception();
        }catch (Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }
    }

    /**
     * Summary:
     * <br>Verifica che il metodo indichi correttamente se ci sono elementi che precedono l'iteratore o meno.
     * <br><br>
     * Design test:
     * <br>Il metodo viene invocato sull'iteratore di una lista vuota e di una non vuota.
     * <br><br>
     * Description:
     * <br>Il metodo viene invocato sull'iteratore di una lista vuota e di una lista non vuota. Se l'iteratore punta all'inizio della lista, allora il metodo restituirà faLse. Se, invece, punta alla fine o a una posizione interna della lista, ritornerà true.
     * <br><br>
     * Preconditions:
     * <br>La lista puà anche essere vuota.
     * <br><br>
     * Postconditions:
     * <br>Il metodo restituisce true se l'iteratore ha elementi che lo precedono, altrimenti false.
     * <br><br>
     * Expected results:
     * <br>False nel caso di lista vuota o nel caso in cui l'iteratore è all'inizio della lista, altrimenti true.
     * <br><br>
     */
    @Test
    public void testHasPrevious() {
        lit= list.listIterator();
        assertFalse(lit.hasPrevious());

        lit= listWithData.listIterator();
        assertFalse(lit.hasPrevious());

        lit= listWithData.listIterator(5);
        assertTrue(lit.hasPrevious());

        lit= listWithData.listIterator(3);
        assertTrue(lit.hasPrevious());
    }

    /**
     * Summary:
     * <br>Il metodo verifica il corretto funzionamento di previous().
     * <br><br>
     * Design test:
     * <br>Invoca il metodo su listIterator di una lista vuota e verifica che venga lanciata correttamente l'eccezione.
     * <br>Successivamente viene verificato il suo funzionamento nel caso di lista non vuota (l'iteratore deve essere decrementato di uno a ogni chiamata al metodo).
     * <br><br>
     * Description:
     * <br>Il metodo viene invocato sul listIterator di una lista vuota e viene verificato il lancio dell'eccezione.
     * <br>Successivamente viene invocato sul listIterator di una lista non vuota. Poiché esso non è all'inizio della lista, ritorna i valori che precedono il listIterator, il quale viene decrementato di uno a ogni chiamata.
     * <br>Infine viene verificato il corretto lancio dell'eccezione, nel caso in cui l'iteratore punti all'inizio di una lista non vuota.
     * <br><br>
     * Preconditions:
     * <br>>La lista può anche essere vuota
     * <br><br>
     * Postconditions:
     * <br>Il metodo ritorna l'elemento che precede l'iteratore (che viene decrementato). Se non ve ne sono, lancia l'eccezione.
     * <br><br>
     * Expected results:
     * <br>Il lancio dell'eccezione se la lista è vuota o l'iteratore è all'inizio della lista, altrimenti l'elemento che precede l'iteratore.
     * <br><br>
     */
    @Test
    public void testPrevious() {
        lit= list.listIterator();
        try{
            lit.previous();
            throw new Exception();
        }catch(Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }

        lit = listWithData.listIterator(5);
        assertEquals(5,lit.previous());
        assertEquals(4,lit.previous());

        lit = listWithData.listIterator();
        try{
            lit.previous();
            throw new Exception();
        }catch (Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }
    }

    /**
     * Summary:
     * <br> Viene verificato che il metodo restituisca la posizione successiva all'iteratore.
     * <br><br>
     * Design test:
     * <br>Il metodo viene invocato sul listIterator di una lista vuota e di una non vuota.
     * <br><br>
     * Description:
     * <br>Il metodo viene chiamato sul listIterator di una lista vuota e si confronta il valore restituito con quello che ci si aspetta. Successivamente esso viene invocato sul listIterator posto alla fine di una lista non vuota. Viene verificato che il valore ritornato sia uguale alla dimensione della lista.
     * <br><br>
     * Preconditions:
     * <br>La lista può anche essere vuota. Il metodo size() è implementato correttamente.
     * <br><br>
     * Postconditions:
     * <br>Iteratore immutato e il valore restituito è (posizione corrente+1).
     * <br><br>
     * Expected results:
     * <br>0 se la lista è vuota, la dimensione della lista se l'iteratore è nell'ultima posizione della lista oppure posizione corrente incrementata di uno, se la posizione dell'iteratore è compresa tra zero e size().
     * <br><br>
     */
    @Test
    public void testNextIndex() {
        lit = list.listIterator();
        assertEquals(0,lit.nextIndex());

        lit = listWithData.listIterator(5);
        assertEquals(listWithData.size(),lit.nextIndex());

        lit = listWithData.listIterator(4);
        assertEquals(5,lit.nextIndex());

    }

    /**
     * Summary:
     * <br>Il metodo verifica che venga restituita la posizione corrente decrementata di uno.
     * <br><br>Design test:
     * <br>Il metodo viene invocato su un listIterator di una lista vuota e di una lista piena. In quest'ultima situazione viene considerato il caso in cui il listIterator sia all'inizio della lista o meno.
     * <br><br>
     * Description:
     * <br>Il metodo viene invocato in pià casi. Viene considerato il caso in cui il listIterator punta a una lista vuota, all'inizio di una lista non vuota e, infine, in una posizione compresa tra zero e size(). Per ogni caso viene svolto un confronto tra valore atteso e quello effettivamente restituito dalla funzione.
     * <br><br>Preconditions:
     * <br>La lista può anche essere vuota.
     * <br><br>
     * Postconditions:
     * <br>Iteratore immutato e valore restituito uguale a (posizione corrente - 1)
     * <br><br>
     * Expected results:
     * <br>Se il listIterator punta a una lista vuota o al primo elemento della lista, ritorna -1; altrimenti (posizione corrente-1).
     * <br><br>
     */
    @Test
    public void testPreviousIndex() {
        lit = listWithData.listIterator(0);
        assertEquals(-1,lit.previousIndex());

        lit = listWithData.listIterator(3);
        assertEquals(2,lit.previousIndex());

        lit= list.listIterator();
        assertEquals(-1, lit.previousIndex());

    }

    /**
     * Summary:
     * <br>Verifica che il metodo rimuova l'elemento puntato dall'iteratore.
     * <br><br>
     * Design test:
     * <br>Il metodo viene invocato sull'iteratore di una lista piena e successivamente sul listIterator di una lista piena.
     * <br><br>
     * Description:
     * <br>Il metodo viene invocato sull'iteratore di una lista piena, senza chiamare prima il metodo next() o previous() e viene verificato il corretto lancio dell'eccezione.
     * <br>Successivamente viene invocato next() e poi il metodo da testare. Si verifica che la dimensione sia diminuita e che sia stato rimosso l'elemento giusto (tramite il confronto dell'array restituito da toArray() con uno creato ad hoc).
     * <br>Dopo il listIterator viene fatto puntare all'elemento di indice 2, viene chiamata il metodo previous() e poi il metodo da testare e si verifica, allo stesso modo del caso precedente, la corretta rimozione dell'elemento.
     * <br>Infine Viene verificato che venga lanciata l'eccezione se, dopo la chiamata a next() e previous(), viene chiamato il metodo add(Object obj).
     * <br><br>
     * Preconditions:
     * <br>I metodi next(), previous() e add(Object obj) sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>Modifica della lista a cui punta l'iteratore, come previsto dal metodo.
     * <br><br>
     * Expected results:
     * <br>L'elemento ritornato da next() o da previous() viene eliminato; altrimenti viene lanciata un'eccezione.
     * <br><br>
     */
    @Test
    public void Remove() {

        it = listWithData.iterator();
        try{
            it.remove();
        }catch(Exception e){
            assertEquals(IllegalStateException.class, e.getClass());
        }

        it.next();
        it.remove();
        assertEquals(4,listWithData.size());
        assertArrayEquals(new Object[]{2,3,4,5}, listWithData.toArray());

        lit = listWithData.listIterator(2);
        lit.previous();
        lit.remove();
        assertEquals(3, listWithData.size());
        assertArrayEquals(new Object[]{2,4,5}, listWithData.toArray());

        lit.add(1);
        try{
            lit.remove();
        }catch(Exception e){
            assertEquals(IllegalStateException.class, e.getClass());
        }
    }

    /**
     * Summary:
     * <br>Verifica che l'elemento restituito dalla chiamata al metodo next() o al metodo previous() venga sostituito dall'oggetto passato come parametro del metodo.
     * <br><br>
     * Design test:
     * <br>Viene invocato il metodo senza prima chiamare next() o previous().
     * <br>Successivamente viene invocato il metodo next(), per due volte, e il metodo remove() e il metodo da testare.
     * <br>Successivamente viene invocato add(Object obj) e poi il metodo set(Object obj).
     * <br>Infine viene considerato il caso valido: viene invocato next() e poi il metodo da testare.
     * <br><br>Description:
     * <br>Viene prima verificato che se chiamo il metodo senza prima chiamare next() o previous(), viene lanciata l'eccezione.
     * <br>Successivamente viene invocato il metodo next(), per due volte, e il metodo remove(). Quindi viene verificato il corretto lancio dell'eccezione.
     * <br>Successivamente viene invocato add(Object obj), quindi viene verificato nuovamente il lancio dell'eccezione.
     * <br>Infine viene considerato il caso valido. Quindi si verifica che le modifche precedenti siano avvenute correttamente e che anche quelle successive avvengano correttamente.
     * <br><br>
     * Preconditions:
     * I metodi next(), remove() e add(Object obj) sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>L'iteratore rimane invariato, ma l'elemento restituito da next() o da previous() viene sostituito da quello passato come parametro.
     * <br><br>
     * Expected results:
     * <br>Valore specificato rimpiazza l'elemento restituito da next() o da previous(). Se tali metodi non sono stati invocati oppure sono stati invocati; ma, prima di invocare set(Object obj), viene invocato add(Object obj) o remove(), allora viene lanciata l'eccezione.
     * <br><br>
     */
    @Test
    public void testSet() {
        lit = listWithData.listIterator();
        try{
            lit.set("Avengers");
        }catch(Exception e){
            assertEquals(IllegalStateException.class, e.getClass());
        }
        lit.next();
        lit.next();
        lit.remove();
        try{
            lit.set("Assemble");
        }catch(Exception e){
            assertEquals(IllegalStateException.class, e.getClass());
        }
        assertArrayEquals(new Object[] {1,3,4,5},listWithData.toArray());

        lit.add(7);
        try{
            lit.set("!!!");
        }catch(Exception e){
            assertEquals(IllegalStateException.class, e.getClass());
        }
        assertArrayEquals(new Object[] {1,7,3,4,5},listWithData.toArray());

        lit = listWithData.listIterator(3);
        lit.next();
        lit.set(7);
        assertArrayEquals(new Object[] {1,7,3,7,5},listWithData.toArray());


    }

    /**
     * Summary:
     * <br>Verifica che il metodo inserisca l'elemento passato come parametro esattamente dove si trova l'iteratore.
     * <br><br>
     * Design test:
     * <br>Il metodo viene invocato su un listIterator di una lista vuota e, successivamente, di una lista non vuota.
     * <br><br>
     * Description:
     * <br>Il metodo viene invocato sul listIterator di una lista vuota e verifico che l'elemento inserito è l'unico elemento presente. Successivamente il metodo viene invocato sull'iteratore di una lista non vuota più volte e vengono svolti alcuni inserimenti. Infine viene verificato (tramite un confronto tra l'array restituito da toArray() e uno creato ad hoc) che gli inserimenti siano avvenuti correttamente.
     * <br><br>
     * <br>Preconditions:
     * <br>La lista può anche essere vuota. I metodi size() e previous() sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>L'elemento specificato viene aggiunto nella posizione in cui si trova l'iteratore.
     * <br><br>
     * Expected results:
     * <br>L'elemento specifivato viene aggiunto nella posizione corrente dell'iteratore.
     * <br><br>
     */
    @Test
    public void testAdd() {

        lit = list.listIterator();
        lit.add(1);
        try{
            lit.next();
            throw new Exception();
        }catch(Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }
        assertEquals(1,lit.previous());
        assertEquals(1,list.size());

        lit = listWithData.listIterator(5);
        lit.add(6);
        assertEquals(6,lit.previous());

        lit = listWithData.listIterator(2);
        lit.add(7);
        assertEquals(7,lit.previous());
        assertArrayEquals(new Object[] {1,2,7,3,4,5,6}, listWithData.toArray());

        lit = listWithData.listIterator();
        lit.add(0);
        assertArrayEquals(new Object[] {0,1,2,7,3,4,5,6}, listWithData.toArray());


    }


}