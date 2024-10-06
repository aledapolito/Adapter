package myTest;

import myAdapter.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Summary:
 * <br>Questa classe testa casi particolari nell'utilizzo di metodi della classe ListAdapter e ListIteratorAdapter.
 * <br><br>
 * Design Test:
 * <br>Ogni test punta a verificare il corretto funzionamento di più metodi assieme, in modo da simulare possibili scenari di utilizzo delle due classi.
 * <br><br>
 * Description:
 * <br>Situazioni generali e casi critici di utilizzo sono testati utilizzando oggetti delle interfacce HList e HListIterator.
 * <br> I test sfruttano oggetti delle classe Integer, String o Double per semplicità.
 * <br> L'utilizzo di tali classi rispetta il tipo dei parametri, in quanto parte della classe Object.
 * <br><br>
 * Preconditions:
 * <br>Prima di ogni test deve essere istanziato un nuovo oggetto di tipo ListAdapter per garantire un corretto controllo delle condizioni possibili del metodo che si sta testando.
 * <br>Ogni variabile deve essere inizializzata  per avere un maggiore controllo sul test.
 * <br> Gli elementi inseriti, o presenti dall'inizio, nelle liste sono noti prima dell'esecuzione del test in modo da poterne confrontare il contenuto e verificare la corretta esecuzione dei metodi.
 * <br><br>
 * Postconditions:
 * <br>La lista modificata deve essere uguale a quella inserita manualmente e gli iteratori si trovano ancora in una posizione valida.
 * <br><br>
 * Execution record:
 * <br>Ogni metodo testato è corretto se tutti i test che verificano il corretto funzionamento hanno un risultato positivo.
 * La corretta esecuzione dell'intero test può essere considerato come record di esecuzione.
 * <br><br>
 * Execution variables:
 * <br>HList list1 - lista vuoto utilizzato per i metodi comuni sia a HListIterator che HIterator
 * <br>HList list2 - lista inizializzata a null
 * <br>HListIterator lit - listIterator inizializzato a null
 * <br><br>
 *
 */
public class TestList {
    HList list1=null;
    HList list2=null;
    HListIterator lit=null;

    static String[] arg = {"barba", "boh", "gianni", "boh", "albero" };

    /**
     * Summary:
     * <br>Metodo per inizializzare le variabili prima dei test.
     * <br><br>
     * Description:
     * <br>Viene creata una nuova lista vuota prima di ogni metodo di test.
     * <br><br>
     * Preconditions:
     * <br>Corretto istanziamento di un oggetto della classe ListAdapter.
     * <br><br>
     * Postconditions:
     * <br> la variabile list1 ha valore diverso da null ed è vuota.
     * <br><br>
     */
    @Before
    public void setup() {
        list1= new ListAdapter();

    }

    /**
     * Summary:
     * <br>Testa i metodi equals(Object obj) e hashCode() tra list, sublist da essa generata e una terza lista contenente gli stessi elementi di sublist.
     * <br><br>
     * Design Test:
     * <br>Aggiunge elementi alla list e calcola il relativo hashCode.
     * <br><br>
     * Description:
     * <br>Si aggiungono elementi alla list principale, si crea un'altra list avente gli stessi elementi della sublist della list principale. Pertanto si confrontano gli hashcode e i risultati del metodo equals(Object obj) tra le tre liste.
     * <br><br>
     * Precondiions:
     * <br> I metodi add(Objct obj), get(int index) sono implementate correttamente e le liste sono inzialmente vuote.
     * <br><br>
     * Postconditions:
     * <br>Le liste che risultano uguali, avranno lo stesso HashCode. Mentre le liste che risultano differenti, avranno HashCode differenti.
     * <br><br>
     * Expected results:
     * <br>Le liste che producono HashCode diversi, risulteranno differenti anche per il metodo equals(Object obj). Altrimenti, risulteranno uguali.
     * <br><br>
     */
    @Test
    public void testSubListHashCodeEquals(){
        for (int i =0; i< arg.length; i++){
            list1.add(arg[i]);
        }
        HList list3 = new ListAdapter();
        list3.add(list1.get(2));
        list3.add(list1.get(3));
        list3.add(list1.get(4));

        list2 = list1.subList(2,5);

        assertEquals(list2.hashCode(), list3.hashCode());
        assertTrue(list2.equals(list3));
        assertEquals(list2.hashCode()==list3.hashCode(),list2.equals(list3) );
        assertTrue(list2.hashCode()!=list1.hashCode());
        assertFalse(list2.equals(list1));
        assertEquals(list2.hashCode()==list1.hashCode(), list2.equals(list1));

    }

    /**
     * Summary:
     * <br>Test sull'applicazione di retainAll(HCollection coll) e removeAll(HCollection coll) di ListAdapter.
     * <br><br>
     * Design Test:
     * <br>Si aggiungono elementi alla lista, si creano due collezioni e poi vengono invocati i due metodi.
     * <br><br>
     * Description:
     * <br>Dopo aver riempito la lista principale, se ne riempie un'altra con gli stessi elementi. Successivamente si creano due collezioni contenenti gli elementi presenti in essa; ma in modo tale che la chiamata a retainAll(coll1) sia equivalente alla chiamata removeAll(coll2).
     * <br><br>
     * Precondiions:
     * <br>Il metodo add(object) è implementato correttamente.
     * <br><br>
     * Postconditions:
     * <br>Le liste contengono gli stessi elementi.
     * <br><br>
     * Expected results:
     * <br>Gli elementi contenuti nella seconda lista sono contenuti anche in quella principale.
     * <br><br>
     */
    @Test
    public void testEquivalenzaRemoveAll_RetainAll(){
        for(int i =0; i< arg.length; i++){
            list1.add(arg[i]);
        }
        list2 = new ListAdapter();
        for(int i =0; i< arg.length; i++){
            list2.add(arg[i]);
        }
        HCollection c1 = new ListAdapter();
        HCollection c2 = new ListAdapter();

        c1.add("barba");
        c1.add("albero");
        c1.add("gianni");
        c2.add("boh");
        list1.removeAll(c1);
        list2.retainAll(c2);
        assertTrue(list1.containsAll(list2));

    }

    /**
     * Summary:
     * <br>Testa i metodi add(int index, Object obj) e addAll(int index,HCollection coll) di ListAdapter.
     * <br><br>
     * Design Test:
     * <br>Si crea una collezione avente solo un elemento e si confronta la chiamata addAll(index, coll) con add(index, obj), ove obj è l'oggetto contenuto in coll.
     * <br><br>
     * Description:
     * <br>Si riempie una seconda lista con gli stessi elementi della principale e si crea una collezione di dimensione uno. Successivamente si chiama su una lista il metodo add(int index, Object obj) e sull'altra il metodo addAll(int index, HCollection coll), ove la collezione contiene solo l'oggetto su cui viene chiamato il primo metodo. Infine si confrontano i risultati.
     * <br><br>
     * Precondiions:
     * <br>Il metodo add(Object obj) è implementato correttamente.
     * <br><br>
     * Postconditions:
     * <br>Le due liste risultano modificate allo stesso modo.
     * <br><br>
     * Expected results:
     * <br>Le due liste risultano uguali e l'oggetto è stato aggiunto alle liste all'indice indicato correttamente.
     * <br><br>
     */
    @Test
    public void testEquivalenza_AddIndex_AddAllIndexSingoloElemento (){
        list2 = new ListAdapter();
        for(int i =0; i< arg.length; i++){
            list2.add(arg[i]);
            list1.add(arg[i]);
        }
        HCollection c = new ListAdapter();
        c.add("casa");
        list1.add(3,"casa");
        list2.addAll(3,c);
        assertTrue(list1.equals(list2));
        assertEquals(list1.get(3), list2.get(3));
    }

    /**
     * Summary:
     * <br>Test dei metodi set(Object obj) di ListIteratorAdapter e set(int index, Object obj) di ListAdapter in caso di sostituzione nella stessa posizione.
     * <br><br>
     * Design Test:
     * <br>Aggiunge gli stessi elementi a due liste e genera un iteratore su una delle due.
     * <br><br>
     * Description:
     * <br>Aggiunge gli elementi a due liste, genera un iteratore a una delle due e si invoca set su quest'ultima. Poi viene invocato set(index, object) sull'altra lista, ove index è l'indice su cui si trova l'iteratore dell'altra lista. Si confrontano le due liste e che l'inserimento sia avvenuto correttamente.
     * <br><br>
     * Precondiions:
     * <br>Il metodo add(Object obj), listIterator(int index), get(int index) e toArray() sono implementati  correttamente.
     * <br><br>
     * Postconditions:
     * <br>Le due liste risultano uguali.
     * <br><br>
     * Expected results:
     * <br>Gli array generati dalle due liste risultano uguali e l'elemento è stato inserito correttamente in entrambi i modi.
     * <br><br>
     */
    @Test
    public void testEquivalenza_IteratorSet_ListSetIndex(){
        list2 = new ListAdapter();
        for(int i =0; i< arg.length; i++){
            list2.add(arg[i]);
            list1.add(arg[i]);
        }
        lit= list1.listIterator(2);
        lit.next();
        lit.set("wow");
        list2.set(2, "wow");
        assertArrayEquals(list1.toArray(), list1.toArray());
        assertEquals("wow", list2.get(2));
        assertEquals(list1.get(2), list2.get(2));
    }

    /**
     * Summary:
     * <br>Test dei metodi remove() di ListIteratorAdapter e remove(int index) di ListAdapter.
     * <br><br>
     * Design Test:
     * <br>Si aggiungono gli stessi elementi ad entrambe le liste e si genera un iteratore su una di esse.
     * <br><br>
     * Description:
     * <br>Dopo aver generato due liste identiche e un iteratore sulla prima, vengono invocati i metodi remove() e remove(index) sulla prima e seconda lista rispettivamente (index è la posizione su cui si trova l'iteratore nella prima lista). Infine si verifica che le liste risultino uguali e che le liste siano state modificate correttamente.
     * <br><br>
     * Precondiions:
     * <br>I metodi add(Object obj), listIterator(int index), next(), toArray() e size() sono implementati correttamente e le liste sono inizialmente vuote.
     * <br><br>
     * Postconditions:
     * <br>Le due liste risultano uguali.
     * <br><br>
     * Expected results:
     * <br>L'uguaglianza tra le due liste è verificata e viene correttamente rimosso l'elemento.
     * <br><br>
     */
    @Test
    public void testEquivalenza_IteratorRemove_ListRemoveIndex(){
        list2 = new ListAdapter();
        for (int i =0; i<arg.length;i++ ){
            list1.add(arg[i]);
            list2.add(arg[i]);
        }
        lit = list1.listIterator(2);
        lit.next();
        lit.remove();
        list2.remove(2);
        assertArrayEquals(list1.toArray(), list2.toArray());
        assertEquals(list2.size(), list1.size());

    }

    /**
     * Summary:
     * <br>Test dei metodi add(Object obj) di ListIteratorAdapter e add(int index, Object obj) di ListIterator.
     * <br><br>
     * Design Test:
     * <br> Il metodo serve a testare il corretto inserimento di elementi in un certo indice nei seguenti casi:
     * <br>1-se c'è un iteratore ad una lista e una lista non vuota;
     * <br>2-se c'è una sublist su cui viene invocato un iteratore e una lista non vuota.
     * <br>In entrambi i casi vengono invocati i metodi add(obj) sull'iteratore e add(index, obj) sulla seconda lista in modo tale che la modifica sia la medesima.
     * <br>Si confrontano i risultati e si verifica che entrambe le liste risultano uguali.
     * <br><br>
     * Description:
     * <br>Vengono riempite le due liste con gli stessi elementi, nello stesso ordine e si invoca un iteratore alla prima lista.
     * <br>Successivamente, alla prima lista viene aggiunto un elemento tramite la chiamata ad add(obj) sull'iteratore e ad add(index, obj) sulla seconda lista, ove index è l'indice dell'iteratore e obj è uguale per entrambi i metodi. Infine si verifica che la modifica sia stata la medesima.
     * <br>Analogamente viene fatto con l'iteratore alla sublist della prima lista e la seconda lista.
     * <br><br>
     * Preconditions:
     * <br>I metodi add(Object obj), get(int index) e toArray() sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br> Gli array generati dalle due liste risultano uguali e l'oggetto è stato inserito nell'indice corretto.
     * <br><br>
     * Expected Results:
     * <br> Le due liste risultano uguali.
     * <br><br>
     */

    @Test
    public void testEquivalenza_iteratorAdd_ListAddIndex(){
        list2= new ListAdapter();
        for (int i =0; i< arg.length;i++){
            list1.add(arg[i]);
            list2.add(arg[i]);
        }
        lit=list1.listIterator(2);
        lit.add("puffo");
        list2.add(2,"puffo");
        assertArrayEquals(list1.toArray(), list2.toArray());
        assertEquals(list1.get(2),list2.get(2));

        HList list3 = new ListAdapter();
        list3= list1.subList(2,4);
        lit= list3.listIterator(3);
        lit.add("buffo");
        list2.add(3,"buffo");
        assertArrayEquals(list1.toArray(), list2.toArray());

    }


    /**
     * Summary:
     * <br>Test del corretto backing della sublist.
     * <br><br>
     * Design Test:
     * <br>Inserimento dei dati in una lista vuota, da cui poi viene generato una sublist. Successivamente vengono aggiunti e rimossi elementi dalla sublist. Infine viene chiamato il metodo clear().
     * <br><br>
     * Description:
     * <br>Si riempie la lista principale e viene generata una seconda lista, sublist della principale. Successivamente viene aggiunto un elemento alla sublist e si verifica che anche la list principale viene modificata. Analogamnete viene fatto per il metodo remove(Object obj). Infine la sublist viene svuotata e si verifica che gli elementi siano stati rimossi anche dalla list principale.
     * <br><br>
     * Precondiions:
     * <br>I metodi add(Object obj), size(), clear(), remove(Object obj), subList(int fromIndex, int toIndex) sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>La dimensione della list e della sua sublist variano in base all'aggiunta o rimozione di elementi.
     * <br><br>
     * Expected results:
     * <br>Tutte le modifiche svolte su sublist, risultano anche sulla list da cui è stata generata.
     * <br><br>
     */
    @Test
    public void Backing(){
        for(int i =0; i< arg.length; i++){
            list1.add(arg[i]);
        }
        int dl1, dl2, dli, dsl1, dsl2, dsli;

        dl1 = list1.size();

        list2 = list1.subList(0, arg.length/2);
        dsl1 = list2.size();
        assertEquals(dsl1, dl1/2 );

        list2.add("sicuro");
        dli= list1.size();
        dsli = list2.size();
        assertEquals(dli, dl1+1);
        assertEquals(dsli, dsl1+1);

        list2.remove("sicuro");
        assertEquals(list1.size(), dl1);
        assertEquals(list2.size(), dsl1);

        list2.clear();
        dl2= list1.size();
        dsl2=list2.size();
        assertEquals(dsl2, 0);
        assertEquals(dl2, (dl1-dsl1));

    }

    /**
     * Summary:
     * <br>Test della corretta esecuzione del sublisting ricorsivo.
     * <br><br>
     * Design Test:
     * <br>Riempimento della lista vuota. Invocazione ricorsiva di subList sulla list principale.
     * <br><br>
     * Description:
     * <br>Dopo aver inserito un po' di elementi nella list principale, viene generata una sublist di tale list con valore di ritorno nella list stessa. Ciò viene svolto più volte. Ad ogni ricorsione si controlla che la dimensione della lista diminuisca del numero di elementi esclusi dalla sublist.
     * <br><br>
     * Precondiions:
     * <br>I metodi add(Object obj), size(), e subList(int fromIndex, int toIndex) sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>La sublist deve avere dimensione pari al valore che ha fermato il ciclo di ricorsione.
     * <br><br>
     * Expected results:
     * <br>La dimensione della lista diminuisce ad ogni ciclo, fino a quando la condizione è soddisfatta.
     * <br><br>
     */
    @Test
    public void  testRecursiveSubList(){

        assertEquals(list1.size(),0);

        int prev = list1.size();
        for(int i=0; i< arg.length; i++){
           list1.add(arg[i]);
        }
        assertEquals(list1.size(), prev+ arg.length);

        prev = list1.size();
        for(int i=0; i< arg.length; i++){
            list1.add(arg[i]);
        }
        assertEquals(list1.size(), prev+ arg.length);

        prev = list1.size();
        for(int i=0; i< arg.length; i++){
            list1.add(arg[i]);
        }
        assertEquals(list1.size(), prev+ arg.length);

        int after=0;
        int count =0;
        while(list1.size()>=2){
            count++;
            prev=list1.size();
            list1=list1.subList(1,prev-1);
            after=list1.size();
            assertEquals(prev-2,after);
        }
        assertEquals(1,list1.size());

    }


    /**
     * Summary:
     * <br>Test dei metodi dell'iteratore.
     * <br><br>
     * Design Test:
     * <br>Si creano tre liste con i rispettivi iteratori e si considerano tre casi differenti di spostamento di quest'ultimo:
     * <br> 1-che l'iteratore sia alla fine della lista e la scorre fino all'inizio e man mano rimuove gli elementi;
     * <br> 2-che l'iteratore sia all'inizio della lista e che la scorre tutta quanto, eliminando man mano gli elementi;
     * <br> 3-che l'iteratore percorre la lista in entrambe le direzioni, ma la lista non subisce modifiche.
     * <br><br>
     * Description:
     * <br>Si creano tre liste e i tre rispettivi iteratori. La prima list viene ispezionata dalla fine all'inizio e ogni elemento ispezionato viene eliminato. La seconda list viene ispezionata nel verso opposto alla prima e viene eliminato ogni elemento della lista. Invece, la terza viene ispezionata in entrambi i versi e non risulta modificata.
     * <br><br>
     * Preconditions:
     * <br>I metodi size(), add(Object obj), listIterator() e listIterator(int index) sono implementati correttamente.
     * <br><br>
     * Postconditions:
     * <br>La prima e la seconda lista risultano vuote, mentre la terza risulta non modificata.
     * <br><br>
     * Expected Results:
     * <br>La dimensione della prima e della seconda lista risultano uguali a zero. L'iteratore della terza lista è arrivato fino alla fine della lista e poi fino all'inizio, senza modificare la lista.
     * <br><br>
     */
    @Test
    public void testIteratore(){

        int dl1 = list1.size();
        list2 = new ListAdapter();
        ListAdapter list3 = new ListAdapter();
        for(int i=0; i< arg.length; i++){
            list1.add(arg[i]);
            list2.add(arg[i]);
            list3.add(arg[i]);
        }
        assertEquals(dl1+arg.length,list1.size());

        //vado indietro e rimuovo man mano
        lit= list1.listIterator(list1.size());
        while(lit.hasPrevious()){
            lit.previous();
            lit.remove();
        }
        assertEquals(0,list1.size());

        //vado avanti e rimuovo man mano
        lit= list2.listIterator();
        while(lit.hasNext()){
            lit.next();
            lit.remove();
        }
        assertEquals( 0,list2.size());

       //prima vado avanti, poi indietro e rimuovo man mano
        lit= list3.listIterator();
        while (lit.hasNext()){
            lit.next();
        }
        assertEquals(lit.nextIndex(), list3.size());

        while (lit.hasPrevious()){
            lit.previous();
        }
        assertEquals(lit.previousIndex(), -1);

        assertEquals(arg.length, list3.size());

    }


}
