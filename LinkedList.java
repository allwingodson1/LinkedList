package linkedlist;

import java.util.Comparator;
import java.util.Iterator;

public class LinkedList<Type> implements Iterable<Type>{
	
	private Element<Type> first;
	private Element<Type> last;
	private int size;
	
	public LinkedList(){
		
	}
	
	public LinkedList(Type[] array){
		for(Type i:array) {
			this.add(i);
		}
	}
	
	public void map(Map<Type> syntax) {
		if(syntax==null)throw new NullPointerException();
		Element<Type> x = first; 
		for(int i=0;i<size;i++) {
			x.item=syntax.hello(x.item);
			x = x.next;
		}
	}

	public LinkedList<Type> filter(Filter<Type> syntax){
		if(syntax==null)throw new NullPointerException();
		LinkedList<Type> temp = new LinkedList<>();
		Element<Type> x = first;
		for(int i=0;i<size;i++) {
			if(syntax.hello(x.item)) {
				temp.add(x.item);
			}
			x = x.next;
		}
		return temp;
	}
	
	public void forEach (ForEach<Type> syntax) {
		if(syntax==null)throw new NullPointerException();
		Element<Type> x = first; 
		for(int i=0;i<size;i++) {
			try {
				syntax.hello(x.item);
			} catch (RuntimeException e) {
				break;
			}
			x = x.next;
		}
	}
	
	private Element<Type> getElement(int index){
		Element<Type> x ;
		if(index>size/2) {
			x = last;
			for(int i=size-2;i>index;i++) {
				x=x.prev;
			}
		}
		else {
			x = first;
			for(int i=0;i<index;i++) {
				x=x.next;
			}
		}
		return x;
	}
	
	public void setAt(Type value,int index) {
		getElement(index).item=value;
	}
	
	public Type get(int index) {
		if(index<0||index>=size) return null;
		return getElement(index).item;
	}
	
	public void add(Type item) {
		size++;
		if(first==null) {
			first = new Element<>(null,item,null);
			last = first;
		}
		else {
			setLast(item);
		}
		
	}
	
	public int length() {
		return size;
	}
	
	public void unshift(Type item) {
		size++;
		Element<Type> temp = new Element<>(null,item,first);
		first.prev=temp;
		first=temp;
	}
	
	public void insert(Type item,int index) {
		checkForRange(index);
		size++;
		if(first==null) {
			first = new Element<>(null,item,null);
			last = first;
		}
		else if(index==0) {
			unshift(item);
			size--;
		}
		else if(index==size-1) {
			setLast(item);
		}
		else if(!(index>=size)) {
			Element<Type> curr = getElement(index);
			Element<Type> prev = curr.prev;
			Element<Type> temp = new Element<>(prev,item,curr);
			curr.prev=temp;
			prev.next=temp;
		}
	}
	
	private void checkForRange(int index) {
		if(index<0||index>size) throw new ArrayIndexOutOfBoundsException();
	}

	private void setLast(Type item) {
		// TODO Auto-generated method stub
		Element<Type> temp = new Element<>(last,item,null);
		getElement(size-2).next = temp;
		last = temp;
	}
	
	public Type pop() {
		if(size==0)return null;
		if(size==1) {
			size=0;
			Type temp=first.item;
			first=null;
			return temp;
		}
		size--;
		Type ans = last.item;
		last.prev.next=null;
		last=last.prev;
		return ans;
	}
	
	public Type delete(int index) {
		checkForRange(index);
		if(index==0)return shift();
		if(index==size-1)return pop();
		size--;
		Element<Type> last = getElement(index);
		Type ans = last.item;
		last.prev.next=last.next;
		last.next.prev=last.prev;
		return ans;
	}
	
	public Type shift() {
		if(size==0)return null;
		if(size==1) {
			size=0;
			Type temp=first.item;
			first=null;
			return temp;
		}
		size--;
		Type ans = first.item;
		first.next.prev=null;
		first=first.next;
		return ans;
	}
	
	public boolean equals(LinkedList<Type> otherList) {
		
		if(size!=otherList.size)return false;
		Element<Type> x = first;
		Element<Type> y = otherList.first;
		
		for(int i=0;i<size;i++) {
			if(x.item!=y.item)return false;
			x = x.next;
			y = y.next;
		}
		
		return true;
	}
	
	public void addAll(Type ...a) {
		for(Type i:a) {
			this.add(i);
		}
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public String toString() {
		String ans = "[";
		Element<Type> x =first;
		for(int i=0;i<size;i++) {
			ans+=x.item;
			x=x.next;
			if(i!=size-1)ans+=", ";
		}
		return ans+"]";
	}
	
	public void sort(Comparator<Type> comp) {
		if(comp == null||first == null) {
			return;
		}
		Element<Type> start = first;
		while(start != null) {
			Element<Type> mystart = start.next;
			while(mystart !=null) {
				if(comp.compare(start.item,mystart.item) > 0) {
					Type temp = start.item ;
					start.item = mystart.item;
					mystart.item = temp;
				}
				mystart = mystart.next;
			}
			start = start.next;
		}
	}

	@Override
	public Iterator<Type> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<Type>() {
			Element<Type> curr = first;
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return curr!=null;
			}

			@Override
			public Type next() {
				// TODO Auto-generated method stub
				Type value = curr.item;
				curr = curr.next;
				return value;
			}
		};
	}

}

class Element<Type>{
	
	public Element<Type> prev;
	public Element<Type> next;
	public Type item;
	
	public Element(Element<Type> prev,Type item,Element<Type> next) {
		this.prev=prev;
		this.next=next;
		this.item=item;
	}

}