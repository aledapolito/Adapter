package myAdapter;


import java.util.NoSuchElementException;
import java.lang.NullPointerException;

/**
 * La classe ListAdapter che implementa le interfacce HList e HCollection di Java2 Collections Framework versione 1.4.2.
 *	Questa classe è la rappresentazione di una lista con un indice alla fine e uno all'inizio.
 */

public class ListAdapter implements HList, HCollection {
    /**
     * variabili private
     */
    private Vector list;
    private int start;
    private int end;
    private ListAdapter root; //puntatore alla lista generatrice. Diversa da null solo se la lista è stata generata dall'invocazione al metodo sublist


    /**
     * Costruttore: crea uno ListAdapter vuoto.
     */
    public ListAdapter() {
        list = new Vector();
        start=0;
        end=0;
        root = null;
    }

    /**
     * Genera uno ListAdapter partendo da una Collection specifica.
     * @param coll collezione specifica, i cui elementi verranno inseriti nella Collection.
     * @throws NullPointerException se l'argomento della funzione non è valido,
     *          altrimenti creo una istanza che contiene tutti gli elementi della collection specifica.
     */

    public ListAdapter(HCollection coll) {
        if (coll==null) throw new NullPointerException();
        list=new Vector();
        root = null;
        Object[] arr = ((ListAdapter) coll).toArray();
        for (int i = 0; i < arr.length; i++) {
            list.addElement(arr[i]);
        }
        end = list.size();
        start =0;
    }

    // Query Operations

    /**
     * Verifica che il ListAdapter sia vuoto.
     *
     * @return true se è vuoto, false altrimenti.
     */
    public boolean isEmpty() {
        return (start==end);
    }

    /**
     * Il metodo ritorna la dimensione del ListAdapter.
     *
     * @return il numero di elementi nella lista.
     */
    public int size() {
        return (end-start);
    }

    /**
     * Ricerca all'interno della lista l'oggetto obj.
     *
     * @param obj elemento che viene ricercato all'interno della lista.
     * @return true se esso è nella lista, altrimenti ritorna false.
     */
    public boolean contains(Object obj) {
        boolean found = false;
        if (obj==null){
            for(int i = start; i<end; i++){
                if(list.elementAt(i)==null) found =true;
            }
        }else{
            for(int i = start; i<end; i++){
                if(obj.equals(list.elementAt(i))) found =true;
            }
        }
        return found;
    }

    /**
     * Crea un iteratore, che punta al primo elemento del ListAdapter.
     *
     * @return iteratore.
     */
    public HIterator iterator() {
        return new ListIteratorAdapter();
    }

    /**
     * Crea un array di dimensione sufficiente, tale da contenere gli elementi del ListAdapter.
     *
     * @return array di tipo Object riempito con gli elementi del ListAdapter.
     */
    public Object[] toArray() {
        Object[] array = new Object[size()];
        for(int i = start; i<end; i++){
            array[i-start]= list.elementAt(i);
        }
        return array;
    }

    /**
     * Il metodo copia gli elementi del ListAdapter all'interno di un array, fornito come parametro.
     * Se esso non è sufficientemente grande, viene generato un nuovo array di dimensione tale da poter contenere
     * tutti gli elementi. Nel caso in cui dovesse essere più grande, le celle rimanenti verranno riempite con elementi di
     * valore null.
     * @param arrayTarget array in cui verranno copiati gli elementi della collection.
     * @throws NullPointerException se l'argomento inserito non è valido.
     * @return array fornito come parametro con gli elementi del ListAdapter al suo interno
     */
    public Object[] toArray(Object[] arrayTarget) {
        if (arrayTarget == null) throw new NullPointerException();
        if (arrayTarget.length<size()){
            Object[] array= toArray();
            arrayTarget = array;
        } else{
            for(int i = start; i<size(); i++){
                arrayTarget[i-start]= list.elementAt(i);
            }
            for(int j = size(); j< arrayTarget.length; j++){
                arrayTarget[j] = null;
            }
        }
        return arrayTarget;
    }

    // Modification Operations

    /**
     * Il metodo aggiunge un elemento, passato come parametro, alla fine del ListAdapter.
     * @param obj elemento che voglio inserire in coda alla collection.
     * @return  true ad azione compiuta.
     */
    public boolean add(Object obj) {
        list.insertElementAt(obj,end);
        end++;
        if (root != null)
            root.end++;
        return true;
    }

    /**
     * Il metodo rimuove la prima occorrenza dell'oggetto obj. Se esso non è presente, il ListAdapter rimane invariato.
     *
     * @return true se l'elemento era presente, altrimenti false.
     */
    public boolean remove(Object obj) {
        if (root==null) {
            if (list.removeElement(obj)) {
                end--;
                return true;
            } else {
                return false;
            }
        }else{
            int i = list.indexOf(obj,start);
            if (i<end && i>=0 ){
                list.removeElementAt(i-start);
                end--;
                root.end--;
                return true;
            }
            return false;
        }
    }

    // Bulk Modification Operations

    /**
     * 	Il metodo verifica che tutti gli elementi presenti in questa specifica collezione siano presenti nel ListAdapter.
     *  @param coll collezione di elementi che vengono cercati all'interno del ListAdapter.
     *  @throws NullPointerException se l'argomento della funzione non è valido.
     * 	@return true se ciò viene verificato, altrimenti ritorna false.
     */
    public boolean containsAll(HCollection coll) {

        if (coll == null) throw new NullPointerException();
        ListAdapter collstack = (ListAdapter) coll;
        Object[] array = collstack.toArray();
        int i = 0;
        boolean found = true;
        while (i < array.length && found ) {
            if (contains(array[i])) {
                i++;
            }else {
                found = false;
            }
        }
        return found;
    }

    /**
     *  Aggiunge in coda tutti gli elementi della Collection specificata nell'argomento del metodo.
     *  @param coll collezione specifica i cui elementi verranno inseriti nello StackAdapter.
     *  @throws NullPointerException se l'argomento della funzione non è valido.
     *  @return true se ciò avviene con successo, altrimenti false.
     *
     */
    public boolean addAll(HCollection coll) {
        if (coll == null) throw new NullPointerException();
        ListAdapter collstack = (ListAdapter) coll;
        Object[] array = collstack.toArray();

        if (array.length ==0) return false;
        for(int i=0; i < array.length;i++ ) {
            add(array[i]);
        }
        return true;

    }

    /**
     * Il metodo inserisce gli elementi della Collection specificata a partire dall'indice index.
     * Se vi sono già degli elementi, essi vengono shiftati a dx.
     * @param index indice di partenza.
     * @param coll  collezione contenente gli elementi che verranno inseriti.
     * @throws NullPointerException se la collezione specifica non è valida.
     * @throws IndexOutOfBoundsException se l'indice non è valido.
     * @return true se l'inserimento avviene con successo, altrimenti false.
     */
    public boolean addAll(int index, HCollection coll) {
        if (coll==null) throw new NullPointerException();
        if (index <0 || index > size()) throw new IndexOutOfBoundsException();

        ListAdapter collstack = (ListAdapter) coll;
        Object[] array = collstack.toArray();
        if (array.length== 0)
            return false;
        else{
            for (int i = 0; i < array.length; i++) {
                add(index, array[i]);
                index++;
            }
            return true;
        }
    }

    /**
     * Elimina dal ListAdapter tutti gli elementi contenuti nella Collection specificata.
     * @param coll collezione i cui elementi verranno rimossi dal ListAdapter.
     * @throws NullPointerException se l'argomento della funzione non è valido.
     * @return true se l'operazione avviene con successo, altrimenti restituisce false.
     */
    public boolean removeAll(HCollection coll) {
        if (coll==null) throw new NullPointerException();
        boolean changed = false;
        if (coll.size()==0) return false;
        else{
            ListAdapter collAd = (ListAdapter) coll;
            Object[] array = collAd.toArray();
            for(int i=0; i < array.length ;i++){
                if (contains(array[i])) {
                    remove(array[i]);
                    changed=true;
                }
            }
        }return changed;
    }

    /**
     * Rimuove dal ListAdapter tutti gli elementi che non sono presenti nella Collection specifica.
     * @param coll collezione di elementi che, se prenti nel ListAdapter, non verranno rimossi.
     * @throws NullPointerException se l'argomento della funzione non è valido.
     * @return true se l'operazione avviene con successo, altrimenti false.
     */
    public boolean retainAll(HCollection coll) {
        if(coll==null) throw new NullPointerException();
        boolean changed = false;
        if (isEmpty()) return changed;
        else{
            ListAdapter collad = (ListAdapter) coll;
            Object[] arr = toArray();
            for(int i =0; i < arr.length; i++){
                if (!collad.contains(arr[i])){
                    remove(arr[i]);
                    changed=true;
                }
            }
        }
        return changed;
    }

    /**
     * Elimina tutti gli elementi del ListAdapter.
     */
    public void clear() {
        if(root == null) {
            list.removeAllElements();
            end=start;
        }else {
            int count = size();
            while(count >0){
                remove(start);
                count--;
            }
        }
    }

    // Comparison and hashing


    /**
     * Ritorna l'hashcode valido per questa lista
     * @return l'hashcode corrispondente alla lista
     */
    public int hashCode() {
        Vector vec = new Vector();
        for(int j = start; j < end; j++){
            vec.addElement(list.elementAt(j));
            j++;
        }
        return vec.hashCode();
    }

    // Positional Access Operations

    /**
     * Ritorna l'elemento corrispondente al specifico index.
     * @param index indice dell'elemento da restituire.
     * @throws IndexOutOfBoundsException se l'indice non è valido.
     * @return l'elemento posizionato nell'indice passato come parametro.
     */
    public Object get(int index) {
        if ((index+start)>end|| index < 0||index>=size()) throw new IndexOutOfBoundsException();
        return list.elementAt(index+start);
    }

    /**
     * Sostituisce l'elemento già presente nell'indice specificato con quello passato come argomento.
     * @param index   indice dell'elemento da sostituire.
     * @param element elemento da inserire nella posizione specificata.
     * @throws IndexOutOfBoundsException se l'indice non è valido.
     * @return l'elemento che si trovava precedentemente nella posizione index.
     */
    public Object set(int index, Object element) {
        Object o;
        if ( index < 0 || index+start>=end||index>=size()) throw new IndexOutOfBoundsException();
        else{
            o = get(index);
            list.setElementAt( element, index+start);
        }
        return o;
    }

    /**
     * Inserisce l'elemento nell'index specificato:
     * 1-verifica che l'index sia valido;
     * 2-se è valido inserisce l'elemento shiftando a dx gli elementi già presenti nella lista.
     * @param index indice di partenza.
     * @param element oggetto che deve essere inserito.
     */
    public void add(int index, Object element) {
        if (index>end||index<start || index < 0) throw new IndexOutOfBoundsException();
        list.insertElementAt(element, index);
        end++;
        if (root!= null) root.end++;
    }

    /**
     * Rimuove l'elemento nella posizione specificata in questo elenco. Sposta tutti gli elementi successivi a sinistra (decrementa di uno i loro indici). Restituisce l'elemento che è stato rimosso dall'elenco.
     * @param index the index of the element to removed.
     * @throws IndexOutOfBoundsException se l'indice non è valido.
     * @return l'elemento rimosso.
     */
    public Object remove(int index) {
        if (index+start>=end || index < 0||index>=size()) throw new IndexOutOfBoundsException();
        Object obj = get(index);
        list.removeElementAt(index+start);
        end--;
        if (root!= null) root.end--;
        return obj;
    }

    // Search Operations

    /**
     * Il metodo restituisce l'indice della prima occorrenza dell' elemento specificato.
     * @param obj elemento che viene cercato.
     * @return l'indice della prima occorrenza, se non presente restituisce -1.
     */
    public int indexOf(Object obj) {
        for (int i = start; i< end; i++){
            if (obj==null) {
                if(list.elementAt(i)==null)
                    return i;
            }else{
                if (obj.equals(list.elementAt(i)))
                    return i;
            }
        }
        return -1;
    }

    /**
     * Il metodo ritorna l'indice dell'ultima occorrenza dell'elemento specificato.
     * @param obj elemento che viene cercato.
     * @return l'indice dell'ultima occorrenza, se non presente restituisce -1.
     */
    public int lastIndexOf(Object obj) {
        int index = -1;
        for (int i = start; i< end; i++){
            if (obj==null) {
                if(list.elementAt(i)==null && i>index)
                    index=i;
            }else{
                if (obj.equals(list.elementAt(i))&&i>index)
                    index=i;
            }
        }
        return index;
    }

    // List Iterators

    /**
     * Il metodo genera un list iterator degli elementi del ListAdapter.
     * @return l'iteratore agli elementi della lista.
     */
    public HListIterator listIterator() {
        return new ListIteratorAdapter();
    }

    /**
     * ritorna un list iterator degli elementi del ListAdapter, partendo da uno specifico index
     * @param index indice del primo elemento restituito da una chiamata di next().
     * @throws IndexOutOfBoundsException se l'indice non è valido.
     * @return l'iteratore posizionato alla posizione specificata.
     */
    public HListIterator listIterator(int index) {
        if(index<0||index<start||index>end) throw new IndexOutOfBoundsException();
        return new ListIteratorAdapter(index);
    }

    // View

    /**
     * Restituisce una sottolista degli elementi compresi tra gli indici indicato, toIndex escluso.
     * Ogni modifica non strutturale effettuata alla sottolista, si ripercuote sulla lista da cui è stata generata e viceversa.
     *
     * @param fromIndex indice di partenza della sublist.
     * @param toIndex   indice finale, non compreso, della sublist.
     * @throws IndexOutOfBoundsException se gli indici passati come parametri non sono validi.
     * @return una sottolista della lista originale contenente i valori compresi tra fromIndex e toIndex, escluso.
     *
     */
    public HList subList(int fromIndex, int toIndex) {
        if (fromIndex<0||toIndex>size()||fromIndex>toIndex) throw new IndexOutOfBoundsException();
        ListAdapter sub= new ListAdapter();
        sub.root=this;
        sub.list= this.list;
        sub.start = fromIndex;
        sub.end=toIndex;
        return sub;
    }


     /**
     * Il metodo restituisce la lista sottoforma di stringa.
     *
     * @return stringa contenente gli elementi presenti nella lista.
     */
    public String toString(){
        String s = "{";
        for (int j = start; j<end; j++){
            s = s + list.elementAt(j) + ";";
        }
        s = s+ " }";
        return s;
    }

    /**
     * Confronta due elementi della stessa classe per verificare che sono uguali.
     * @param obj oggetto che viene comparato.
     * @return true se e solo se i due oggetti confrontati sono equivalenti, altrimenti false.
     */
    public boolean equals(Object obj) {

        if (obj == null || obj.getClass()!=getClass()) return false;

        if (((ListAdapter) obj).size() != size()) return false;

        if (((ListAdapter) obj).size() == size() && size()==0) return true;

        Object[] arr = ((ListAdapter) obj).toArray();
        boolean valido = true;
        int i = start;
        while (valido && i<size()){
            if (arr[i-start].equals(list.elementAt(i))){
                i++;
            }else{
                valido =false;
            }
        }
        return valido;
    }


    /**
     * Private class dell'iteratore, che implementa le interfacce HListIterator e HIterator
     */
    private class ListIteratorAdapter implements HIterator, HListIterator {

        /**
         * cursore dell'iteratore
         */
        private int point;

        /**
         * Flag per il controllo validità di set(int index, Object obj) e remove().
         * Nel costruttore viene inizializzato a false, ma esso viene impostato a true dai metodi previous() e next().
         */
        boolean modified = false;

        /**
         * Flag per decidere quale elemento rimuovere nel metodo remove:
         * true: per rimuovere l'elemento successivo,
         * false: per rimuovere l'elemento precedente.
         */
        boolean foward = false;

        /**
         * Costruttore
         */

        public ListIteratorAdapter() {
            point = 0;
        }

        /**
         * Costruttore con parametri.
         *
         * @param i indice ove punta l'iteratore.
         * @throws IndexOutOfBoundsException se il parametro non è valido.
         */
        public ListIteratorAdapter(int i) {
            if(i<start || i>end){
                throw new IndexOutOfBoundsException();
            }
            point = i;
        }

        /**
         * Metodo che verifica se l'iteratore ha elementi a cui puntare se si muove in avanti lungo la lista.
         *
         * @return true se ci sono elementi proseguendo in avanti lungo la lista, false altrimenti.
         */
        public boolean hasNext() {
            if (point == size()) return false;
            return true;
        }

        /**
         * Questo metodo ritorna l'elemento successivo all'iteratore e poi incrementato di uno l'iteratore.
         *
         * @return l'oggetto successivo a quello puntato dall'iteratore.
         * @throws NoSuchElementException se l'iteratore è alla fine della lista.
         */
        public Object next() {
            if (!hasNext()) throw new NoSuchElementException();
            else {
                point++;
                modified = true;
                foward = true;
                return	get(point-1);
            }
        }

        /**
         * Questo metodo verifica che vi siano elementi che precedono l'iteratore.
         *
         * @return true se esiste un elemento nella posizione precedente, false altrimenti.
         */
        public boolean hasPrevious() {
            if (point == 0) return false;
            return true;
        }

        /**
         * Il metodo restituisce l'elemento che precede l'iteratore e decrementa di uno l'iteratore.
         *
         * @return l'oggetto che precede quello puntato dall'iteratore.
         * @throws NoSuchElementException se non ve ne sono.
         */
        public Object previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            else {
                point--;
                modified = true;
                foward = false;
                return get(point);
            }
        }

        /**
         * Il metodo ritorna l'indice successivo a quello in cui è posizionato l'iteratore.
         *
         * @return intero che indica la posizione successiva a quella dell'iteratore, size() se l'iteratore è nell'ultima posizione valida.
         */
        public int nextIndex() {
            if (point==size()) return size();
            return point+1;
        }

        /**
         * Il metodo restituisce l'indice precedente all'iteratore.
         *
         * @return intero che indica la posizione precedente a quella dell'iteratore, -1 se l'iteratore si trova all'inizio della lista.
         */
        public int previousIndex() {
            if (point==0) return -1;
            return point-1;
        }

        /**
         * Il metodo rimuove l'ultimo elemento che è stato ritornato da next() o da previous().
         *
         * @throws IllegalStateException se, dopo la chiamata a previous() o a next(), è stata chiamata la funzione ListIterator.add()
         *                               oppure se nè previous() o next() sono stati chiamati precedentemente.
         */
        public void remove() {
            if (!modified) throw new IllegalStateException();
            if (!foward) {
                ListAdapter.this.remove(point);
            } else {
                ListAdapter.this.remove(point-1);
                point--;
            }
            modified = false;
        }

        /**
         * Sostituisce l'ultimo elemento ritornato da next() o da previous() con l'elemento che viene passato per argomento.
         *
         * @param obj l'elemento con cui sostituire l'ultimo elemento restituito da next() o previous().
         * @throws IllegalStateException se nè next o previous sono stati chiamati precedentemente oppure se, dopo la loro chiamata,
         *                               sono state invocate le funzioni ListIterator.add() o ListIterator.remove()
         */
        public void set(Object obj) {
            if (!modified) throw new IllegalStateException();
            if (foward) {
                ListAdapter.this.set(point-1, obj);
            }else{
                ListAdapter.this.set(point, obj);
            }
            modified=true;
        }

        /**
         * Il metodo inserisce l'elemento che viene passato come argomento esattamente dove si trova l'iteratore.
         * Se la lista non contiene nessun elemento, allora questo elemento sarà l'unico della lista.
         *
         * @param obj elemento da inserire nella posizione corrente dell'iteratore.
         */
        public void add(Object obj) {
            ListAdapter.this.add(point, obj);
            point++;
            modified = false;
        }
    }

}

