package myTest;


import myAdapter.HCollection;
import myAdapter.HList;
import myAdapter.ListAdapter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Summary:
 * <br> Questa classe testa tutti i metodi della classe ListAdapter, tranne quelli di ListIteratorAdapter.
 * <br><br>
 * Design test:
 * <br>In questa classe ogni test punta a verificare il corretto funzionamento per ogni singolo metodo. Le variabili vengono resettate così da garantire un corretto test di tutte le condizioni possibili.
 * <br><br>
 * Description:
 * <br> La classe ListAdapter implementa HList and HCollection, soprattutto i metodi HCollection sono testati usando un HCollection come object.
 * Per semplicità, i valori inseriti sono tutti interi appartenenti alla classe Integer (che è comunque parte della classe Object, pertanto rispetta il tipo dei parametri da inserire).
 * <br><br>
 * Preconditions:
 * <br> Un nuovo oggetto vuoto di tipo ListAdapter deve sempre essere creato prima di ogni test.
 * <br> Le variabili vengono sempre inizializzate, a meno che non voglia verificare il caso in cui siano uguali a null.
 * <br> Metodi che prendono come parametri classi che implementano HCollection sono considerati idonei per questa interfaccia,
 * soprattutto non lanciano l'eccezione ClassCastException.
 * <br>Tutti gli elementi contenuti nella lista sono conosciuti a priori, così da poter verificare il corretto funzionamento dei metodi.
 * <br><br>
 * Postconditions:
 * <br>I metodi implementati modificano la lista nel modo atteso: gli elementi contenuti sono esattamente quelli previsti.
 * <br><br>
 * Execution record:
 * <br>Ogni metodo testato è corretto se tutti i test che verificano il corretto funzionamento hanno un risultato positivo.
 * <br>La corretta esecuzione dell'intero test può essere considerato come record di esecuzione.
 * <br><br>
 * Execution variables:
 * <br>HList list - lista vuota per tutti i metodi comuni alle interfacce HCollection e HList.
 * <br>HList listWithData - lista non vuota con cui vengono testati i metodi forniti dall'interfaccia HListIterator.
 * <br>HCollection coll - oggetto usato per testare i metodi forniti dalla interfaccia HCollection
 * <br><br>
 *
 *
 * @see myAdapter.HList
 * @see myAdapter.HCollection
 */

public class ListAdapterTest {
    HList list = null;
    HList listWithData= null;
    HCollection coll=null;

    /**
     * Summary:
     * <br> Metodo per inizializzare le variabili prima dei test.
     * <br><br>
     * Description:
     * <br>Viene creata una nuova Collezione vuota prima di ogni metodo di test,
     * in questo modo la collezione su cui vengono invocati i vari metodi testati ha sempre uno stato valido.
     * <br>Viene anche creato una lista vuota e una riempita.
     *  <br><br>
     */
    @Before
    public void setup() {
        coll = new ListAdapter();
        list = new ListAdapter();
        listWithData = new ListAdapter();

        for (int i = 0; i < 5; i++)
            listWithData.add(i + 1);
    }

    /**
     * Summary:
     * <br> Test che verifica la corretta creazione di una istanza di tipo ListAdapter che contiene un valore della collection, passato come parametro.
     * <br><br>
     * Design test:
     * <br> Si verifica la creazione di un ListAdapter nel caso in cui la collezione passata come parametro è null, vuota e piena
     * <br><br>
     * Description:
     * <br>Si verifica il lancio dell'eccezione, nel caso di collezione null. Successivamente si aggiungono elementi alla collezione e si invoca il costruttore, passando come parametro la collezione contenente gli elementi. Infine si confrontano i rispettivi array, ottenuti tramite la chiamata a toArray().
     * <br><br>
     * Preconditions:
     * <br> I metodi toArray() e add(Object obj) sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br> La nuova collezione deve essere identica a quella passata come parametro.
     * <br><br>
     * Expected Results:
     * <br> Le due collezioni devono essere identiche.
     *  <br><br>
     */
    @Test
    public void testConstructorWithParameter() {

        HCollection notValid;
        try{
            notValid = new ListAdapter(null);
        }catch (Exception e){
            assertEquals(NullPointerException.class, e.getClass());
        }

        ListAdapter newCollection1 = new ListAdapter(coll);
        assertArrayEquals(coll.toArray(), newCollection1.toArray());

        coll.add(1);
        coll.add(2);
        coll.add(3);
        coll.add(4);

        ListAdapter newCollection2 = new ListAdapter(coll);
        assertArrayEquals(coll.toArray(), newCollection2.toArray());


    }

    /**
     * Summary:
     * <br>Il test verifica che il metodo toArray() ritorni un array contenente tutti gli oggetti presenti nella collezione, nell'ordine in cui sono inseriti.
     * <br><br>
     * Design test:
     * <br> Dopo che alcuni dati vengono inseriti, un array creato manualmente viene comparato con quello ritornato dal metodo
     * <br><br>
     * Description:
     * <br> Dopo aver inserito o rimosso alcuni dati nella collezione, viene verificato che l'oggetto ritornato da toArray() corrisponda all'array di Object
     * contenente gli elementi nello stesso ordine in cui sono stati inseriti nella collezione.
     * <br><br>
     * Preconditions:
     * <br> Il metodo add(Object obj) è implementato correttamente.
     * <br><br>
     * Postconditions:
     * <br>Il metodo restituisce un array di Objects contenente tutti gli elementi contenuti nella collection, nell'ordine in cui essi stono stati originariamente inseriti. Se invece la collection è vuota, l'array sarà vuoto.
     * <br><br>
     * Expected Results:
     * <br>L'array ottenuto dal metodo e quello creato manualmente sono uguali.
     *  <br><br>
     */
    @Test
    public void testToArray() {
        coll.add(1);
        coll.add(2);
        coll.add(3);
        coll.add(4);

        assertArrayEquals(new Object[]{1, 2, 3, 4}, coll.toArray());

        coll = new ListAdapter();
        assertArrayEquals(new Object[0], coll.toArray());
    }

    /**
     * Summary:
     * <br>Test del metodo toArray(Object[] arrayTarget), che ritorna l'array, fornito come parametro della funzione, contenente tutti gli oggetti presenti nella collezione.
     * <br><br>
     * Design Test:
     * <br>Niente viene inserito dentro la collezione e un array parzialmente occupato viene passato come parametro.
     * <br>Alcuni elementi sono inseriti nella collezione e un array riempito solo in parte di dimensione maggiore è passato come parametro.
     * <br>Alcuni elementi sono inseriti nella collezione e un array riempito solo in parte di dimensione minore è passato come parametro. Viene anche testato il corretto lancio dell'eccezione nel caso in cui il parametro sia null.
     * <br><br>
     * Description:
     * <br>Per tutte e tre le situazioni elencate nel Design, viene verificato usando un array creato manualmente.
     * <br><br>
     * Preconditions:
     * <br>Il metodo add(Object obj) è implementato correttamente.
     * <br><br>
     * Postconditions:
     * <br>L'array che viene restituito deve contenere tutti gli elementi presenti nella collezione sulla quale viene invocato il metodo
     * e nell'ordine in cui compaiono nella collezione.
     * <br><br>
     * Expected results:
     * <br>Il metodo ritorna l'array passato come parametro riempito con gli elementi della collezione. Se la collezione è vuota, l'array fornito come parametro non viene modificato.
     * <br><br>
     */
    @Test
    public void testToArrayWithParameter() {
        Object[] array1 = null;

        try {
            coll.toArray(array1);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        array1 = new Object[]{1, 2, 3};
        assertArrayEquals(new Object[]{null, null, null}, coll.toArray(array1));

        coll.add(5);
        coll.add(6);
        assertArrayEquals(new Object[]{5, 6, null}, coll.toArray(array1));

        coll.add(7);
        coll.add(8);
        assertArrayEquals(new Object[]{5, 6, 7, 8}, coll.toArray(array1));
    }

    /**
     * Summary:
     * <br>Il metodo testa il corretto funzionamento del metodo add(Object obj), che aggiunge l' elemento passato come parametro alla fine del ListAdapter.
     * <br><br>
     * Design test:
     * <br>Gli elementi vengono aggiunti alla collezione. Poi l'array, restituito dalla chiamata a toArray(), viene poi confrontato con uno creato manualmente (contiene gli elementi che sono stati inseriti nella collezione, nell'ordine in cui è avvenuto l'inserimento).
     * <br><br>
     * Description:
     * <br>Dopo aver aggiunto dei valori, viene verificato - confrontando l'array ritornato da toArray() con uno creato a mano - che gli elementi sono stati inseriti correttamente.
     * <br><br>
     * Preconditions:
     * <br>Il metodo toArray() è implementato correttamente.
     * <br><br>
     * Postconditions:
     * <br>La collezione deve contenere gli oggetti passati come parametri, nell'ordine in cui sono inseriti.
     * <br><br>
     * Expected results:
     * <br>Gli elementi dell'array devono essere nella collezione nello stesso ordine in cui sono inseriti.
     * <br><br>
     */
    @Test
    public void testAdd() {
        coll.add(3);
        coll.add(null);
        coll.add(6);
        coll.add("last");

        assertArrayEquals(new Object[]{3,null,6,"last"}, coll.toArray());
    }

    /**
     * Summary:
     * <br>Verifica che il metodo testato rimuova l'elemento passato come parametro. Se questo non è presente nella collezione, restituisce false.
     * <br><br>
     * Design test:
     * <br>Il metodo viene chiamato sulla collezione vuota.
     * <br>Il metodo viene invocato su una collezione non vuota; ma passando come argomento un elemento non contenuto nella collezione.
     * <br>Il metodo viene invocato su una collezione non vuota, passando come argomento un elemento contenuto nella collezione.
     * <br><br>
     * Description:
     * <br>Il metodo viene invocato su una collezione vuota, successivamente vengono aggiunti elementi alla collezione, anche ripetuti, e il metodo viene invocato, due volte con elementi presenti nel contenitore e una volta con un elemento non presente. Infine viene verificato che la collezione abbia il numero di elementi atteso e nell'ordine corretto (Così da verificare che sia stata rimossa la prima occorrenza dell'elemento specifico).
     * <br><br>
     * Preconditions:
     * <br>I metodi add(Object obj), toArray() e size() devono funzionare correttamente.
     * <br><br>
     * Postconditions:
     * <br> Dopo aver invocato il metodo, la collezione non deve contenere l'elemento passato come parametro, restituendo true. Se la collezione non viene modificata, restituisce false.
     * <br><br>
     * Expected results:
     * <br>Se l'elemento non è presente o la collezione è vuota ritorna false. Altrimenti viene rimossa la prima occorrenza dell'oggetto passato come parametro.
     * <br><br>
     */
    @Test
    public void testRemove() {
        assertFalse(coll.remove(5));

        coll.add(2);
        coll.add(3);
        coll.add(2);
        coll.add(null);
        assertFalse(coll.remove(7));
        assertTrue(coll.remove(3));
        assertTrue(coll.remove(2));
        assertEquals(2, coll.size());
        assertArrayEquals(new Object[]{2, null}, coll.toArray());

    }
    /**
     * Summary:
     * <br>Metodo che verifica che la lista è vuota o meno.
     * <br><br>
     * Design test:
     * <br>Viene aggiunto un elemento nella collezione vuota e viene testato, l'elemento viene rimosso è viene nuovamente invocato il metodo.
     * <br><br>
     * Description:
     * <br>Dopo aver aggiunto un elemento viene verificato che il contenitore non risulti vuoto, poi esso viene rimosso e viene verificato che esso risulti vuoto.
     * <br><br>
     * Preconditions:
     * <br>Il metodo add(Object obj) e remove(Object obj) sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>Il metodo deve ritornare true se il contenitore non contiene elementi.
     * <br><br>
     * Expected results:
     * <br> False dopo aver inserito un elemento, true dopo averlo rimosso.
     * <br><br>
     */
    @Test
    public void testIsEmpty() {
        coll.add(1);
        assertFalse(" la collezione non è vuota  ", coll.isEmpty());
        coll.remove(1);
        assertTrue("la collezione è vuota ", coll.isEmpty());

    }


    /**
     * Summary:
     * <br>Metodo che ritorna il corretta numero di elementi all'interno del contenitore.
     * <br><br>
     * Design test:
     * <br>Test della corretta dimensione della collezione, testando anche il suo aumento quando vengono aggiunti elementi e il suo decremento dopo la rimozione di uno di essi.
     * <br><br>
     * Description:
     * <br>Il metodo size() viene invocato prima e dopo aver aggiunto o rimosso elementi.
     * <br><br>
     * Preconditions:
     * <br>I metodi remove(Object obj) e add(Object obj) sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>Il valore di ritorno deve corrispondere al numero di elementi presenti all'interno della collezione.
     * <br><br>
     * Expected results:
     * <br>0 se la collezione è vuota, altrimenti il numero di elementi presenti nella collezione.
     * <br>La dimensione della collezione deve variare man mano che inserisco o rimuovo elementi.
     * <br><br>
     */
    @Test
    public void testSize() {
        assertEquals(0, coll.size());
        coll.add(1);
        assertEquals(1,coll.size());
        coll.add(3);
        coll.add(5);
        assertEquals(3,coll.size());
        coll.remove(5);
        assertEquals(2, coll.size());
    }

    /**
     * Summary:
     * <br>Il test verifica che il metodo ritorna true se il contenitore contiene l'elemento specificato.
     * <br><br>
     * Design test:
     * <br>Dopo aver aggiunto due elementi, verifico la presenza di alcuni elementi e l'assenza di altri.
     * <br><br>
     * Description:
     * <br>Dopo aver aggiunto due elementi, si verifica che il metodo ritorna true se uno dei due viene passato come parametro, altrimenti false.
     * <br><br>
     * Preconditions:
     * <br>Il metodo add(Object obj) è implementato correttamente.
     * <br><br>
     * Postconditions:
     * <br>Il metodo ritorna true se l'elemento passato come argomento è effettivamente contenuto, false altrimenti.
     * <br><br>
     * Expected results:
     * <br>False se l'elemento non è presente nella collezione, true altrimenti.
     * <br><br>
     */
    @Test
    public void testContains() {
        coll.add(1);
        coll.add(3);
        coll.add(4.5);
        coll.add(null);
        assertTrue("la collezione contiene 1",coll.contains(1));
        assertTrue("la coll contiene 4.5 ", coll.contains(4.50));
        assertTrue("nullpointerexception", coll.contains(null));
        assertFalse("coll non contiene 2", coll.contains(2));
        assertFalse("coll contiene pippo", coll.contains("pippo"));
    }

    /**
     * Summary:
     * <br>Il test verifica che la collezione contiene tutti gli elementi della collezione passata come parametro.
     * <br><br>
     * Design test:
     * <br>Gli elementi sono aggiunti alla collezione sulla quale il metodo verrà invocato.
     * <br>Successivamente verrà creata una collezione contenente tutti gli elementi della collezione principale e un'altra nella quale vengono inseriti sia elementi presenti che assenti nella collezione principale.
     * <br>Viene poi invocato il metodo e si verifica che la collezione principale contenga tutti gli elementi della prima collezione creata manualmente e non tutti quelli della seconda.
     * <br>Viene anche testato il lancio di una eccezione nel caso la collezione passata come parametro sia non valida (=null).
     * <br><br>
     * Description:
     * <br>Dopo aver testato il lancio dell'eccezione, sono stati aggiunti degli elementi alla collezione principale e sono state create due collezioni, una passando come parametro al costruttore la collezione principale, l'altra aggiungendo manualmente degli elementi
     * sia contenuti che non nella collezione principale.
     * <br>Poi verifica che con la prima collezione il risultato sia true e nel secondo sia false.
     * <br><br>
     * Preconditions:
     * <br>Il metodo add(Object obj) deve funzionare correttamente.
     * <br><br>
     * Postconditions:
     * <br>Il metodo deve restituire true se la collezione contiene tutti gli elementi della collezione passata come parametro al metodo.
     * <br><br>
     * Expected results:
     * <br>Il risultato è true se e solo se la collezione passata come parametro è un sottoinsieme della collezione principale.
     * <br><br>
     */
    @Test
    public void testContainsAll() {
        try {
            list.containsAll(null);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        coll.add(1);
        coll.add(2);
        coll.add(3);
        coll.add(4);
        coll.add(null);

        HCollection testColl1 = new ListAdapter(coll);

        assertTrue(coll.containsAll(testColl1));

        HCollection testColl2 = new ListAdapter();
        testColl2.add(1);
        testColl2.add(3);
        assertTrue(coll.containsAll(testColl2));

        testColl2.add(8);
        testColl2.add(5);
        testColl2.add(null);
        assertFalse(coll.containsAll(testColl2));

    }

    /**
     * Summary:
     * <br>Il test verifica che il metodo AddAll(HCollection coll) inserisca tutti gli elementi contenuti nella collezione passata come parametro nella collezione su cui il metodo è invocato.
     * <br><br>
     * Design test:
     * <br>Inizialmente viene verificato che il metodo lanci l'eccezione se la collezione ha valore null.
     * <br> Poi viene verificato che essa ritorni false nel caso in cui la collezione passata come parametro sia vuota.
     * <br> Successivamente viene creata una collezione in cui vengono inseriti degli elementi.
     * <br> Questa collezione viene usata come parametro del metodo da testare, invocato sulla collezione principale.
     * <br> Infine si verifica che effettivamente tutti gli elementi siano presenti nella collezione principale.
     * <br><br>Description:
     * <br>Il metodo viene invocato sulla collezione con valore null per verificare il lancio dell'eccezione.
     * <br>Il metodo ha come parametro una collezione vuota, pertanto ritorna false.
     * <br>Il metodo viene invocato su una collezione non vuota, pertanto il metodo ritornerà true e gli elementi verranno inseriti nella collezione principale.
     * <br><br>
     * Preconditions:
     * <br>I metodi add(Object obj) e containsAll(HCollection coll) sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>Tutti gli elementi della collezione devono appartene anche alla collezione principale.
     * <br><br>
     * Expected results:
     * <br>La collezione contiene tutti gli elementi della collezione passata come parametro.
     * <br><br>
     */
    @Test
    public void testAddAll() {
        HCollection testColl = null;

        try {
            coll.addAll(testColl);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        HCollection testColl1 = new ListAdapter();
        assertFalse(coll.addAll(testColl1));

        HCollection testColl2 = new ListAdapter();
        testColl2.add(1);
        testColl2.add(2);
        testColl2.add(3);

        coll.addAll(testColl2);
        assertTrue(coll.containsAll(testColl2));

    }

    /**
     * Summary:
     * <br>Il test verifica che il metodo AddAll(int index, HCollection coll) inserisca nel ListAdapter tutti gli elementi contenuti nella collezione, passata come parametro, a partire dall'indice specificato.
     * <br><br>
     * Design test:
     * <br>Inizialmente viene verificato che il metodo lanci l'eccezione se la collezione ha valore null o se l'indice non è valido.
     * <br>Poi viene verificato che essa ritorni false nel caso in cui la collazione passata come parametro sia vuota.
     * <br>Poi viene creata una collezione in cui vengono successivamente inseriti degli elementi.
     * <br>Questa collezione viene usata come parametro del metodo da testare, insieme all'indice di partenza, invocandolo sulla lista vuota.
     * <br>Successivamente si verifica che effettivamente tutti gli elementi siano presenti nella lista.
     * <br><br>
     * Description:
     * <br>Il metodo viene invocato nel caso in cui la collezione è null, per verificare il lancio dell'eccezione.
     * <br>Il metodo viene invocato nel caso di indice non valido, per verificare il lancio dell'eccezione.
     * <br>Il metodo ha come parametro una collezione vuota, pertanto ritorna false.
     * <br>Il metodo viene invocato passando come parametro una collezione non vuota e un indice valido.
     * <br><br>
     * Preconditions:
     * <br>I metodi add(Object obj), toArray(), size() e containsAll(HCollection coll) sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>Tutti gli elementi della collection devono appartene anche alla collection principale. Se la lista era inizialmente vuota, gli array generati saranno uguali. Altrimenti gli elementi vengono shiftati a destra e si ottiene una lista più grande.
     * <br><br>
     * Expected results:
     * <br>La collezione contiene tutti gli elementi della collezione passata come parametro. Inoltre gli array generati risultano uguali se la lista principale era vuota. Altrimenti, l'array generato è uguale a quello creato manualmente.
     * <br><br>
     */
    @Test
    public void testAddAllIndex() {
        HCollection testColl = null;

        try {
            list.addAll(0,testColl);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        try {
            list.addAll(list.size()+1,coll);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        HCollection testColl1 = new ListAdapter();
        assertEquals(list.size(), 0);
        assertFalse(list.addAll(0,testColl1));
        assertArrayEquals(list.toArray(), testColl1.toArray());


        HCollection testColl2 = new ListAdapter();
        testColl2.add(1);
        testColl2.add(2);
        testColl2.add(3);

        list.addAll(0,testColl2);
        assertTrue(list.containsAll(testColl2));
        assertArrayEquals(list.toArray(), testColl2.toArray() );

        assertTrue(listWithData.addAll(2,testColl2));

        assertArrayEquals(new Object[] {1,2,1,2,3,3,4,5}, listWithData.toArray());
    }
    /**
     * Summary:
     * <br>Il test verifica che il metodo rimuova dalla collezione principale tutti gli elementi presenti nella collezione passata come parametro.
     * <br><br>Design test:
     * <br>Si verifica che se viene passata una collezione vuota, la collezione principale non viene modificata.
     * <br>Verifica che se viene passata come parametro una collezione i cui elementi non sono nella collezione principale, quest'ultima non viene modificata.
     * <br>Verifica che, se una collezione con elementi in comune viene passata, essi vengono eliminati.
     * <br><br>
     * Description:
     * <br>Viene verificato il corretto lancio dell'eccezione, nel caso in cui il parametro è null.
     * <br>Viene verificato il corretto funzionamento, nel caso in cui la collezione passata come parametro e quella principale sono vuote.
     * <br>Viene verificata la restituzione di false nel caso non vi siano elementi in comune tra le due collezioni.
     * <br>Successivamente, viene aggiunto un altro elemento, presente anche nella collezione principale. Si verifica che l'invocazione al metodo testato ritorni true, e che la lista principale sia stata modificata correttamente.
     * <br><br>Preconditions:
     * <br>I metodi add(Object obj) e toArray() sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>Nella collezione principale non ci devono essere elementi contenuti nella collezione passata come parametro.
     * <br><br>
     * Expected results:
     * <br>False se la collezione passata come parametro è vuota o non ha nessun elemento in comune con la collezione principale, true altrimenti.
     * <br><br>
     */
    @Test
    public void testRemoveAll() {
        HCollection testColl = null;

        try {
            coll.removeAll(testColl);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        testColl = new ListAdapter();
        assertFalse(coll.removeAll(testColl));

        coll.add(1);
        coll.add(4);
        coll.add(3);
        coll.add(2);

        testColl.add(5);
        assertFalse(coll.removeAll(testColl));
        assertArrayEquals(new Object[]{1, 4, 3, 2}, coll.toArray());


        testColl.add(2);
        assertTrue(coll.removeAll(testColl));
        assertArrayEquals(new Object[]{1,4,3}, coll.toArray());

        coll.add(null);
        testColl.add(null);
        coll.removeAll(testColl);
        assertArrayEquals(new Object[]{1,4,3}, coll.toArray());
    }

    /**
     * Summary:
     * <br> Verifica che il metodo rimuova dalla lista principale tutti gli elementi non in comune con la collezione passata come parametro.
     * <br><br>
     * Design test:
     * <br>Si verifica che il metodo restituisca false, se la collezione principale e/o la collezione passata come parametro sono vuote.
     * <br>Si verifica che il metodo restituisca true quando la collezione principale è piena e la collezione passata come parametro vuota. In questo caso viene anche verificato che tutti gli elementi della collezione principale vengano rimossi.
     * <br>Si verifica il caso in cui le collezioni hanno un elemento in comune e il caso in cui non hanno alcun elemento in comune.
     * <br><br>Description:
     * <br> La collezione principale è sempre riempita con gli stessi valori; mentre la collezione passata come parametro contiene sempre una combinazione diversa di valori. Così è possibile verificare che il metodo lavori correttamente nei diversi casi.
     * <br><br>Preconditions:
     * <br> I metodi size(), add(Object obj), toArray() e clear()  sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br> La lista principale deve contenere solo gli elementi contenuti anche nella collezione passata come parametro e deve tornare true se la lista principale viene modificata.
     * <br><br>
     * Expected results:
     * <br> La lista principale deve avere dimensione zero se non ci sono elementi in comune, altrimenti ci devono essere solo gli elementi in comune con la collezione passata come parametro.
     * <br><br>
     */
    @Test
    public void testRetainAll() {
        HCollection testColl = new ListAdapter();

        assertFalse(coll.retainAll(testColl));

        coll.add(1);
        coll.add(2);
        coll.add(3);
        assertTrue(coll.retainAll(testColl));
        assertEquals(0, coll.size());

        coll.add(2);
        coll.add(3);
        testColl.add(2);
        assertTrue(coll.retainAll(testColl));
        assertArrayEquals(new Object[]{2}, coll.toArray());
        coll.clear();

        coll.add(1);
        coll.add(1);
        coll.add(1);
        assertTrue(coll.retainAll(testColl));
        assertEquals(0, coll.size());
    }
    /**
     * Summary:
     * <br>Il test verifica che il metodo elimini tutti gli elementi nella collezione in cui viene chiamato.
     * <br><br>
     * Design test:
     * <br>Viene verificato che il metodo funzioni anche nel caso in cui la collezione sia vuota.
     * <br>Successivamente viene verificato che il metodo funzioni anche nel caso in cui la collezione non è vuota.
     * <br><br>Description: il metodo viene invocato sulla collezione, inizialmente vuota. Successivamente essa viene riempita e il metodo viene invocato. Per verificare che la collezione sia vuota, si verifica che la sua dimensione sia uguale a zero.
     * <br><br>Preconditions:
     * <br>I metodi add(Object obj) e size() sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>La collezione non deve contenere nessun elemento.
     * <br><br>
     * Expected results:
     * <br>La dimensione della collezione è uguale a zero.
     *  <br><br>
     */
    @Test
    public void testClear() {
        coll.clear();
        assertEquals(0, coll.size());

        coll.add(13);
        coll.add(2);
        coll.add("hulk");
        coll.add("spacca");
        coll.clear();
        assertEquals(0, coll.size());

    }

    /**
     * Summary:
     * <br>Il metodo deve ritornare true se la dimensione delle due collezioni è uguale e gli stessi elementi sono contenuti nello stesso ordine.
     * <br><br>
     * Design test:
     * <br>Vengono inseriti gli stessi elementi e confrontati sia nella collezione principale che in quella creata. Viene testato anche l'inserimento di tipi incompatibili.
     * <br><br>Description:
     * <br>Dopo la creazione della nuova collezione, gli stessi elementi vengono inseriti in entrambe le collezioni nello stesto ordine. Poi i due contenitori vengono confrontati.
     * <br>Quindi viene aggiunto un elemento in uno dei due e vengono confrontati nuovamente.
     * <br>La seconda collezione viene poi svuotata e riempita con gli stessi elementi, ma in ordine opposto. I contenitori vengono nuovamente confrontati.
     * <br>Successivamente la seconda collezione viene riempita con elementi, sia contenuti che non, nella collezione principale e viene svolto un confronto.
     * <br>Poi la collezione principale viene riempita con elementi contenuti nella nuova collezione, ma non nello stesso ordine e viene svolto un confronto.
     * <br>Infine confronta la collezione con un array di Object.
     * <br><br>Preconditions:
     * <br>I metodi add(Object obj) e clear() sono implementati correttamente.
     * <br><br>Postconditions:
     * <br>I metodi devono ritornare true se le due collezioni hanno gli stessi elementi nello stesso ordine e deve ritornare false se i due oggetti sono incompatibili -difatti se gli oggetti non sono dello stesso tipo, non possono essere uguali-.
     * <br><br>Expected results:
     * <br>True solo nel primo caso descritto e nel caso di elementi compatibili, false negli altri casi descritti.
     *  <br><br>
     */
    @Test
    public void testEquals() {
        HCollection coll2 = new ListAdapter();
        coll.add(1);
        coll.add(2);
        coll.add(3);

        coll2.add(1);
        coll2.add(2);
        coll2.add(3);

        assertTrue(coll.equals(coll2));

        coll2.add(4);
        assertFalse(coll.equals(coll2));

        coll2.clear();
        coll2.add(3);
        coll2.add(2);
        coll2.add(1);
        assertFalse(coll.equals(coll2));

        coll2.clear();
        coll2.add(1);
        coll2.add(4);
        coll2.add(3);
        assertFalse(coll.equals(coll2));


        coll.add(1);
        coll.add(3);
        coll.add(2);
        assertFalse(coll.equals(coll2));

        assertFalse(coll.equals(new Object[]{1, 2, 3, 1, 3, 2}));
        assertArrayEquals(new Object[]{1, 2, 3, 1, 3, 2}, coll.toArray() );
    }

    /**
     * Summary:
     * <br> Verifica del calcolo dell'hashCode.
     * <br><br>
     * Design test:
     * <br> Vengono inseriti gli stessi dati nella collezione principale e in una appena creata. L'hashCode deve essere uguale. Poi il metodo viene ritestato invertendo l'ordine degli elementi.
     * <br><br>Description:
     * <br> Dopo aver inserito gli elementi nella collezione principale, ne viene creata una seconda che contiene gli stessi elementi della prima nello stesso ordine. Il risultato deve essere lo stesso. Successivamente viene invertito l'ordine di due elementi e viene ricalcolato l'hashCode. Esso risulta diverso da quello della collezione principale.
     * <br><br>Preconditions:
     * <br> Il metodo add(Object obj) è implementato correttamente.
     * <br><br>
     * Postconditions:
     * <br> Solo se gli oggetti confrontati hanno gli stessi elementi disposti nello stesso ordine, l'hashCode deve essere identico.
     * <br><br>Expected results:
     * <br>L'hashCode di due oggetti coincide quando gli elementi sono uguali e la loro disposizione all'interno dei due oggetti è la stessa.
     *  <br><br>
     */
    @Test
    public void testHashCode() {
        coll.add(1);
        coll.add(2);
        coll.add(3);

        HCollection coll2 = new ListAdapter();
        coll2.add(1);
        coll2.add(2);
        coll2.add(3);

        assertEquals(coll2.hashCode(), coll.hashCode());

        coll2.clear();
        coll2.add(1);
        coll2.add(3);
        coll2.add(2);
        assertNotEquals(coll2.hashCode(),coll.hashCode());

        coll2.clear();
        assertNotEquals(coll2.hashCode(), coll.hashCode());

        HCollection coll3 = new ListAdapter();
        coll3.add(1);
        coll3.add(2);
        coll3.add(3);
        assertEquals(coll3.hashCode(), coll.hashCode());
    }

    /**
     * Summary:
     * <br> Test che verifica il corretto funzionamento della funzione get(int index).
     * <br><br>
     * Design test:
     * <br>Dopo aver inserito alcuni elementi, si verifica che il metodo restituisca l'elemento nella posizione passata come parametro. Viene anche verificato il corretto lancio di un'eccezione.
     * <br><br>
     * Description:
     * <br> Dopo aver inserito manualmente gli elementi nella lista, il metodo viene invocato passando come parametro la dimensione del contenitore. Poi viene utilizzato un ciclo for, per verificare che il metodo restituisca i valori attesi.
     * <br><br>
     * Preconditions:
     * <br>I metodi add(Object obj), size(), get(int index) sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>L'elemento restituito deve essere quello contenuto nella posizione specificata nell'argomento del metodo.
     * <br><br>Expected results:
     * <br>Il ciclo for termina correttamente.
     *  <br><br>
     */
    @Test
    public void testGet() {
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        try {
            list.get(list.size());
            throw new NullPointerException();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        for (int i = 0; i < 5; i++)
            assertEquals(i, list.get(i));
    }

    /**
     * <br><br>
     * Summary:
     * <br>Il metodo deve sostituire correttamente l'elemento nella posizione, passata come parametro, con l'oggetto specificato.
     * <br><br>Design test:
     * <br>Dopo aver creato un'altra lista, esse vengono riempite con n valori. Successivamente si verifica che gli elementi vengano sostituiti correttamente.
     * <br><br>
     * Description:
     * <br>Dopo aver riempito entrambe le liste (quello principale e quello appena creato), il metodo viene invocato passando come parametro un indice non valido, così da poter verificare il corretto lancio dell'eccezione.
     * <br>Successivamente il metodo viene invocato in un ciclo for, così da modificare completamente il contenuto della lista principale e renderla uguale all'altra lista.
     * <br><br>Preconditions:
     * <br>I metodi add(Object obj), size() e equals(Object obj) sono implementati correttamente.
     * <br><br>Postconditions:
     * <br>Le due liste sono uguali, grazie al corretto funzionamento del metodo nel ciclo for.
     * <br><br>Expected results:
     * <br>Nel caso di indici non validi, deve essere lanciata correttamente l'eccezione. Altrimenti, il metodo equals deve ritornare true.
     *  <br><br>
     */
    @Test
    public void testSet() {

        HList list2 = new ListAdapter();
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);

        list2.add(1);
        list2.add(2);
        list2.add(3);
        list2.add(4);
        list2.add(5);

        try {
            list.set(list.size(), "error");
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        for (int i = 0; i < list.size(); i++)
            list.set(i, i + 1);

        assertEquals(list, list2);
    }
    /**
     * Summary:
     * <br> Il metodo deve inserire l'oggetto nella posizione specificata.
     * <br><br>Design test:
     * <br>Nella lista non vuota vengono sostituiti degli elementi e viene verificato che la dimensione si incrementi di uno e che la sostituzione sia avvenuta correttamente.
     * <br><br>Description:
     * <br>Il metodo verifica che la chiamata alla funzione add(int index, Object obj) inserisca l'elemento speficicato nella posizione specificata.
     * <br>Questo viene verificato ad ogni inserimento, confrontando l'array restituito da toArray() con un array creato sul momento, contenente gli elementi nella posizione attesa.
     * <br>In particolare, viene verificato il corretto funzionamento del metodo nel caso di inserimento all'inizio e alla fine della lista.
     * <br>Viene anche verificato il corretto lancio dell'eccezione.
     * <br><br>Preconditions:
     * <br>L'indice deve essere positivo e minore o uguale della dimensione della lista.
     * <br>I metodi size(), toArray() e clear() devono funzionare correttamente.
     * <br><br>Postconditions:
     * <br>Se la posizione è valida, gli elementi devono essere aggiunti in quella specifica locazione.
     * <br><br>Expected results:
     * <br>I due array (quello creato a mano e quello generato da toArray()) devono risultare uguali.
     *  <br><br>
     */
    @Test
    public void testAddIndex() {

        listWithData.add(0, 0);
        assertEquals(6, listWithData.size());
        assertArrayEquals(new Object[]{0, 1, 2, 3, 4, 5}, listWithData.toArray());

        listWithData.add(3, 2.5);
        assertEquals(7, listWithData.size());
        assertArrayEquals(new Object[]{0, 1, 2, 2.5, 3, 4, 5}, listWithData.toArray());

        listWithData.add(listWithData.size(), 5.5);
        assertEquals(8, listWithData.size());
        assertArrayEquals(new Object[]{0, 1, 2, 2.5, 3, 4, 5, 5.5}, listWithData.toArray());

        try {
            listWithData.add(-1, "Exception");
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        listWithData.clear();
        listWithData.add(0, "wiped");
        assertArrayEquals(new Object[]{"wiped"}, listWithData.toArray());

    }

    /**
     * Summary:
     * <br>Si verifica che il metodo rimuova l'elemento nella posizione specificata.
     * <br><br>
     * Design test:
     * <br>Dopo aver aggiunto valori alla lista vuota, viene creata una nuova lista e si verifica che esse sono differenti.
     * <br>Successivamente si rimuovono alcuni elementi dalla lista principale e il confronto viene nuovamente svolto.
     * <br><br>
     * Description:
     * <br>Dopo aver aggiunto valori alla lista principale e aver creato un array che contiene solo alcuni elementi contenuti in quella principale, si verifica che risulteranno uguali solo nel momento in cui gli elementi non in comune vengono rimossi dalla lista principale.
     * <br>Viene testato anche il lancio dell'eccezione, nel caso in cui la lista è vuoto e nel caso di indice non valido.
     * <br>Si verifica anche che gli elementi vengono shiftati a sinistra per ogni rimozione.
     * <br><br>
     * Preconditions:
     * <br>I metodi add(Object obj), equals(Object obj) e size() devono funzionare correttamente.
     * <br><br>
     * Postconditions:
     * <br> Vengono rimossi solo gli elementi situati nella posizione passata come parametro.
     * <br><br>
     * Expected results:
     * <br>Le liste devono risultare uguali.
     *  <br><br>
     */
    @Test
    public void testRemoveIndex() {
        try {
            list.remove(0);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        try {
            list.remove(list.size());
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        HList list2 = new ListAdapter();
        list2.add(1);
        list2.add(3);
        list2.add(5);

        assertNotEquals(list,list2);
        assertArrayEquals(list.toArray(), new Object[] {1,2,3,4,5,6});
        list.remove(1);
        assertArrayEquals(list.toArray(), new Object[] {1,3,4,5,6});
        list.remove(2);
        assertArrayEquals(list.toArray(), new Object[] {1,3,5,6});
        list.remove(3);

        assertEquals(list, list2);

    }

    /**
     * Summary:
     * <br>Il metodo verifica che l'indice restituito dal metodo indexOf(Object obj) sia corretto (deve corrispondere all'indice della prima occorrenza dell'oggetto obj)
     * <br><br>Design test:
     * <br>Il metodo viene chiamato sulla lista vuota. Dopo vengono inseriti alcuni elementi (non tutti diversi tra di loro) e il metodo viene chiamato, passando come parametro gli elementi duplicati. Viene anche considerato il caso in cui l'elemento non sia presente nella lista.
     * <br><br>Description:
     * <br>Inizialmente il metodo viene invocato su una lista vuota. Successivamente vengono inseriti dei valori nella lista e il metodo viene invocato più volte. Il metodo viene invocato nei casi in cui l'elemento specificato appartiene alla collezione ma non è ripetuto, nel caso in cui è ripetuto e nel caso in cui non è presente.
     * <br><br>Preconditions:
     * <br>Il metodo add(Object obj) è implementato correttamente.
     * <br><br>Postconditions:
     * <br>Viene restituito l'indice della prima occorrenza dell'elemento passato come parametro, altrimenti -1.
     * <br><br>Expected results:
     * <br> -1 per una lista vuota; l'indice della prima occorrenza dell'elemento (0-based e partendo dall'inizio dello stack e non dalla cima).
     *  <br><br>
     */
    @Test
    public void testIndexOf() {
        assertEquals(-1, list.indexOf(9));

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(1);
        list.add(5);

        assertEquals(1, list.indexOf(2));
        assertEquals(0, list.indexOf(1));
        assertEquals(-1, list.indexOf(6));
    }

    /**
     * Summary:
     * <br> Viene testato il metodo che restituisce l'indice dell'ultima occorrenza dell'elemento passato come parametro.
     * <br><br>
     * Design test:
     * <br>Viene verificato il caso in cui la lista è vuota. Dopo vengono inseriti degli elementi nella lista e viene verificato che il valore restituito dal metodo (passando come parametro gli elementi duplicati) sia quello atteso.
     * <br><br>
     * Description:
     * <br>Il primo test viene svolto su una lista vuota.
     * <br>Le successive invocazioni vengono svolte dopo aver inserito dei valori, alcuni dei quali sono anche ripetuti. Si considerano i casi di elementi ripetuti, non ripetuti e non presenti nella lista.
     * <br><br>Preconditions:
     * <br>Il metodo add(Object obj) è implementato correttamente.
     * <br><br>
     * Postconditions:
     * <br> Viene restituito l'indice dell'ultima occorrenza dell' elemento uguale a quello passato come parametro, -1 se on c'è tale indice.
     * <br><br>
     * Expected results:
     * <br>-1 per una lista vuota o per elemento non trovato, altrimenti l'indice dell'ultima occorrenza dell'elemento specificato (0-based e partendo dall'inizio della lista, non dalla cima).
     * <br><br>
     */
    @Test
    public void testLastIndexOf() {
        assertEquals(-1, list.lastIndexOf(9));

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(1);
        list.add(5);

        assertEquals(1, list.lastIndexOf(2));
        assertEquals(3, list.lastIndexOf(1));
        assertEquals(-1, list.lastIndexOf(6));
    }

    /**
     * Summary:
     * <br> Il metodo verifica il corretto funzionamento del metodo toString().
     * <br><br>Design test:
     * <br>Invocazione del metodo sia nel caso di lista vuota che non.
     * <br><br>Description:
     * <br>Il metodo viene prima invocato sulla lista vuota. Successivamente viene inserito un elemento nella lista e il metodo viene nuovamente invocato.
     * <br><br>Preconditions:
     * <br>La funzione add(Object obj) è implementato correttamente.
     * <br><br>Postconditions:
     * <br>La stringa risultante è uguale a quella inserita manualmente.
     * <br><br>Expected results:
     * <br>La stringa contenente la rappresentazione della lista.
     *  <br><br>
     */
    @Test
    public void testToString(){
        assertEquals("{ }", list.toString());
        list.add(1);
        assertEquals("{1; }", list.toString());
    }

    /**
     * Summary:
     * <br> Il metodo verifica la corretta creazione di una sottolista della lista di partenza.
     * <br><br>Design test:
     * <br>Il metodo viene invocato passando come parametro due indici validi e viene verificato confrontando l'array (restituito da toArray()) con un array contenente i valori che la sottolista dovrebbe contenere. Viene testato anche con una sottolista vuota (se i due indici passati come parametro sono uguali), e il corretto lancio di una eccezione se gli indici non sono validi.
     * <br><br>Description:
     * <br>Le verifiche al metodo vengono svolte sulla dimensione e gli elementi della sottolista.
     * <br>Prima viene verificato che passando come parametri due indici uguali, la sottolista risulti vuota.
     * <br>Poi, presi due indici diversi e validi, si verifica che la sottolista contenga gli elementi compresi tra quegli indici (l'ultimo escluso).
     * <br>Viene verificato il corretto lancio di una eccezione nel caso di indici non validi.
     * <br>Infine viene verificato il corretto funzionamento di alcuni metodi della classe ListAdapter e se viene modificata anche la lista generatrice della sottolista.
     * <br><br>Preconditions:
     * <br>I metodi size() e toArray() devono funzionare correttamente.
     * <br><br>Postconditions:
     * <br> La lista principale non viene modificata dopo l'invocazione del metodo. Con gli indici from = 0 e to = size() la sottolista risulta uguale alla lista.
     * <br><br>Expected results:
     * <br>La sottolista contiene tutti gli elementi della lista nell'intervallo [from, to)
     *  <br><br>
     *
     */
    @Test
    public void testSubList() {
        list = listWithData.subList(2, 2);
        assertEquals(0, list.size());

        list = listWithData.subList(2, 4);
        assertEquals(2, list.size());
        assertArrayEquals(new Object[]{3, 4}, list.toArray());

        list = listWithData.subList(0, listWithData.size());
        assertEquals(listWithData, list);

        listWithData.clear();
        list = listWithData.subList(0, 0);
        try {
            list = listWithData.subList(2, 3);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        //isEmpty()
        assertTrue(list.isEmpty());
        //size()
        assertEquals(0,list.size());
        //add(obj)
        list.add(2);
        list.add(4);
        list.add(6);
        list.add(8);
        list.add(5);
        assertArrayEquals(new Object[]{2,4,6,8,5}, list.toArray());
        assertArrayEquals(new Object[]{2,4,6,8,5}, listWithData.toArray()); //anche la lista da cui è stata generata la sottolista è stata modificata
        list.add(1);
        list.add(3);
        list.add(7);
        assertArrayEquals(new Object[]{2,4,6,8,5,1,3,7}, list.toArray());
        assertArrayEquals(new Object[]{2,4,6,8,5,1,3,7}, listWithData.toArray());
        //remove(index)
        list.remove(2);
        assertArrayEquals(new Object[]{2,4,8,5,1,3,7}, list.toArray());
        assertArrayEquals(new Object[]{2,4,8,5,1,3,7}, listWithData.toArray());
        //containsAll(coll)
        try {
            list.containsAll(null);
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        coll.add(8);
        coll.add(2);
        coll.add(5);
        assertTrue(list.containsAll(coll));
        //addAll(coll)
        try{
            list.addAll(null);
        }catch(Exception e){
            assertEquals(NullPointerException.class, e.getClass());
        }

        coll.clear();
        coll.add(11);
        coll.add(13);
        coll.add(17);
        list.addAll(coll);
        assertTrue(list.containsAll(coll));
        //removeAll(coll)
        list.removeAll(coll);
        assertFalse(list.containsAll(coll));
        assertFalse(listWithData.containsAll(coll));


    }

}
