package main;

public class PriorityQueue <E extends Comparable<? super E>> extends java.util.ArrayList<E> {
	public int size;
	
	public PriorityQueue() {
		size = 0;
	}
	
	public boolean add(E object) {
		if(object == null) {
			throw new NullPointerException();
		}
		if(this.isEmpty()) {
			super.add(object);
			size++;
			return true;
		}
		super.add(object);
		size++;
		if(addHelper(size-1, get(size-1))) {
			return true;
		}

		return false;
	}

	public boolean addHelper(int index, E object) {
		if(index == 0) {
			if(get(index).compareTo(get(0)) == 0) {
				return true;
			}
		}
		if(index % 2 == 1) {
			if(get(index).compareTo(get(index/2)) < 0) {
				E temp = this.get(index/2);
				set(index/2, object);
				set(index, temp);
				return addHelper(index/2, object);
			}
			return true;
		}else if(index % 2 == 0){
			if(get(index).compareTo(get((index/2)-1)) < 0) {
				E temp = this.get((index/2)-1);
				set((index/2)-1, object);
				set(index, temp);
				return addHelper((index/2)-1, object);
			}
			return true;
		}
		return true;
	}
	
	public boolean remove(E object) {
		if(object == null) {
			throw new NullPointerException();
		}
		if(size == 1 && object.compareTo(get(0)) == 0) {
			super.remove(0);
			size--;
			return true;
		}
		if(this.contains(object)) {
			int index = indexOf(object);
			set(index, get(size-1));
			super.remove(size-1);
			size--;
			return removeHelper(index, get(index));
		}
		return false;
	}
	
	public boolean removeHelper(int index, E object) {
		if((index*2)+2 < size) {
			E leftChild = get((index*2)+1);
			E rightChild = get((index*2)+2);
			if(leftChild.compareTo(rightChild) < 0) {
				if(object.compareTo(leftChild) > 0) {
					set(index, leftChild);
					set((index*2)+1, object);
					return removeHelper((index*2)+1, object);
				}
				return true;
			}else {
				if(object.compareTo(rightChild) > 0) {
					set(index, rightChild);
					set((index*2)+2, object);
					return removeHelper((index*2)+2, object);
				}
				return true;
			}
		}else if((index*2)+1 < size) {
			E leftChild = get((index*2)+1);
			if(object.compareTo(leftChild) > 0) {
				set(index, leftChild);
				set((index*2)+1, object);
				return true;
			}
			
			return true;
		}
		
		
		return true;
	}
	
	public boolean offer(E object) {
		if(object == null) {
			throw new NullPointerException();
		}
		return add(object);
	}
	
	public E peek() {
		if(this.isEmpty()) {
			return null;
		}
		return this.get(0);
	}
	
	public E poll() {
		if(this.isEmpty()) {
			return null;
		}
		E temp = this.get(0);
		remove(temp);
		return temp;
	}
	
	public int size() {
		return size;
	}
	
	
	
}