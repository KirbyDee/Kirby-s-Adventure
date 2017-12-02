package com.kirbydee.hud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

import com.kirbydee.entity.movable.living.LivingEntity;
import lombok.Data;

@Data
public class HUD implements Iterable<HUDBar<? extends LivingEntity>> {

	private List<HUDBar<? extends LivingEntity>> bars;
	
	public HUD() {
		this(2);
	}
	
	public HUD(int initialCapacity) {
		this.bars = new ArrayList<>(initialCapacity);
	}
	
	public void addBar(HUDBar<? extends LivingEntity> bar) {
		bar.setOffset(numberOfBars());
		bars.add(bar);
	}
	
	public int numberOfBars() {
		return bars.size();
	}
	
	@Override
	public Iterator<HUDBar<? extends LivingEntity>> iterator() {
		return bars.iterator();
	}
	
	@Override
	public void forEach(Consumer<? super HUDBar<? extends LivingEntity>> action) {
		bars.forEach(action);
	}

	@Override
	public Spliterator<HUDBar<? extends LivingEntity>> spliterator() {
		return bars.spliterator();
	}
}
