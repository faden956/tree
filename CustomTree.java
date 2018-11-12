import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable , Serializable {

    Entry<String> root;

    @Override
    public String get(int index){
        throw new UnsupportedOperationException();
    }
    @Override
    public String set(int index, String element){
        throw new UnsupportedOperationException();
    }
    @Override
    public void add(int index, String element){
        throw new UnsupportedOperationException();
    }
    @Override
    public String remove(int index){
        throw new UnsupportedOperationException();
    }
    @Override
    public List<String> subList(int fromIndex, int toIndex){
        throw new UnsupportedOperationException();
    }
    @Override
    protected void removeRange(int fromIndex, int toIndex){
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean addAll(int index, Collection<? extends String> c){
        throw new UnsupportedOperationException();
    }


    public CustomTree(){
        root = new Entry<>("root");
    }

    static class Entry<T> implements Serializable {
        String elementName;
        int LineNumber;
        boolean availableToAddLeftChildren ,availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry (String name) {
            elementName = name;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        public void checkChildren(){
            if(leftChild != null){
                availableToAddLeftChildren = false;
            } else  availableToAddLeftChildren = true;
            if (rightChild != null){
                availableToAddRightChildren = false;
            } else availableToAddRightChildren = true;

        }

        public boolean isAvailableToAddChildren(){
            return availableToAddLeftChildren | availableToAddRightChildren;
        }
    }

    @Override
    public boolean add(String element){
        List<Entry> list = new ArrayList<>();
        list.add(root);
        for (int i = 0; i < list.size(); i++) {
            Entry entry = list.get(i);
            if(entry.availableToAddLeftChildren ) {
                entry.leftChild = new Entry<>(element);
                entry.leftChild.parent = entry;
                entry.checkChildren();
                return true;
             }
             if (entry.availableToAddRightChildren) {
                 entry.rightChild = new Entry<>(element);
                 entry.rightChild.parent = entry;
                 entry.checkChildren();
                 return true;
             }
             if(entry.leftChild != null)
                list.add(entry.leftChild);
             if(entry.rightChild != null)
                list.add(entry.rightChild);
        }
        for (Entry entry : list) {
            entry.checkChildren();
        }

        return add(element);
    }

    @Override
    public int size(){
        List<Entry> list = new ArrayList<>();
        list.add(root);
        for (int i = 0; i < list.size(); i++) {
            Entry entry = list.get(i);
            if(entry.leftChild != null)
                list.add(entry.leftChild);
            if(entry.rightChild != null)
                list.add(entry.rightChild);
        }
        return list.size() - 1 ;
    }

    public String getParent(String s){
        List<Entry> list = new ArrayList<>();
        list.add(root);
        for (int i = 0; i < list.size(); i++) {
            Entry entry = list.get(i);
            if (entry.elementName.equals(s)){
                return entry.parent.elementName;
            }

            if(!entry.availableToAddLeftChildren && entry.leftChild != null){
                list.add(entry.leftChild);
            }
            if (!entry.availableToAddRightChildren  && entry.rightChild != null) {
                list.add(entry.rightChild);
            }
        }
      return null;
    }
    @Override
    public boolean remove(Object o){
        if(!o.getClass().getSimpleName().equals("String")) {
            throw  new UnsupportedOperationException();
        }
        List<Entry> list = new ArrayList<>();
        list.add(root);
        for (int i = 0; i < list.size(); i++) {
            Entry entry = list.get(i);
            if (entry.elementName.equals(o)){
                if(entry.parent.leftChild != null && entry.parent.leftChild.elementName.equals(o))
                    entry.parent.leftChild = null;
                if(entry.parent.rightChild != null && entry.parent.rightChild.elementName.equals(o))
                    entry.parent.rightChild = null;
                entry = null;
                return true;
            }
            if(entry.leftChild != null)
                list.add(entry.leftChild);
            if (entry.rightChild != null)
                list.add(entry.rightChild);
        }
        return false;
    }
}
//Необходимо реализовать метод remove(Object o), который будет удалять элемент дерева имя которого было полученного в качестве параметра.
//
//Если переданный объект не является строкой, метод должен бросить UnsupportedOperationException.
//
//Если в дереве присутствует несколько элементов с переданным именем - можешь удалить только первый найденный.