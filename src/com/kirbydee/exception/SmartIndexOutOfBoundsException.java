package com.kirbydee.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper=false)
@RequiredArgsConstructor
@SuppressWarnings("serial")
public class SmartIndexOutOfBoundsException extends IndexOutOfBoundsException {
	/**
	 * The lower bound.
	 */
	@Getter
	private int lowerBound;
	
	/**
	 * The upper bound.
	 */
	@Getter
	private int upperBound;
	
	/**
	 * The index.
	 */
	@Getter
	private int index;

	/**
	 * Construct an SmartIndexOutOfBoundsException.
	 * 
	 * @param lowerBound 	The lowest legal index value
	 * @param upperBound 	The highest legal index value plus one
	 * @param index 		The actual index value
	 */
	public SmartIndexOutOfBoundsException(int lowerBound, int upperBound, int index) {
		// Generate a detail message that captures the failure
		super("Lower bound: " 		+ lowerBound +
				", Upper bound: " 	+ upperBound +
				", Index: " 		+ index);
		
		// Save failure information for programmatic access
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.index = index;
	}
}
