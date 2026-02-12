/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.observer;

import javax.security.auth.x500.X500Principal;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import com.iluwatar.observer.generic.GenHobbits;
import com.iluwatar.observer.generic.GenOrcs;
import com.iluwatar.observer.generic.GenWeather;
import lombok.extern.slf4j.Slf4j;

/**
 * The Observer pattern is a software design pattern in which an object, called the subject,
 * maintains a list of its dependents, called observers, and notifies them automatically of any
 * state changes, usually by calling one of their methods. It is mainly used to implement
 * distributed event handling systems. The Observer pattern is also a key part in the familiar
 * model–view–controller (MVC) architectural pattern. The Observer pattern is implemented in
 * numerous programming libraries and systems, including almost all GUI toolkits.
 *
 * <p>In this example {@link Weather} has a state that can be observed. The {@link Orcs} and {@link
 * Hobbits} register as observers and receive notifications when the {@link Weather} changes.
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    Weather weather = new Weather();
    Weather weather1 = new Weather();
    weather1.addObserver(new OrcsObserver());
    weather1.addObserver(new HobbitsObserver());
    weather.addObserver(new Elves());

    weather.timePasses();
    TribeObserver[] arr=new TribeObserver[3];
    arr[0]=new OrcsObserver();
    arr[1]=new HobbitsObserver();
    arr[2]=new Elves();
    TribeObserver x= arr[1];
    assert (x instanceof com.iluwatar.observer.HobbitsObserver)&& !(x instanceof com.iluwatar.observer.Elves)&& !(x instanceof com.iluwatar.observer.OrcsObserver);
    x.reactionStrength();
    GenWeather genericWeather = new GenWeather();
    // Generic observer inspired by Java Generics and Collections by Naftalin & Wadler
    LOGGER.info("--Running generic version--");
  }
}
abstract class TribeObserver implements WeatherObserver
{
	protected int mood;
	public TribeObserver(int initialMood) {
    this.mood=initialMood;
	}
	public int reactionStrength()
	{
		return baseStrength()* strategyMultiplier();
	}
	protected int baseStrength()
	{
		return Math.max(1,mood);
	}
	protected int strategyMultiplier()
	{
		return 1;
	}
}
class Orcs extends TribeObserver{
	public Orcs() {
		super(10);
	}

	@Override
	public void update(WeatherType currentWeather) {
		LOGGER.info("Orcs observer weather now", currentWeather));
	    if(currentWeather ==WeatherType.WINDY||currentWeather ==WeatherType.COLD)
	    {
	    mood+=2;}
	    else {
	    {
	    mood+=1;}
		}
	  }
	  @Override
	  protected int strategyMultiplier()
	  {
		  return 3;
	  }
	}
class Hobbits extends TribeObserver{
	public Hobbits() {
		super(5);
	}

	@Override
	public void update(WeatherType currentWeather) {
	    LOGGER.info("Hobbit observer weather now", currentWeather);
	    if(currentWeather ==WeatherType.SUNNY)
	    {
	    mood+=1;}
	    else {
	    {
	    mood+=0;}
		}
	  }
	 @Override
	  protected int strategyMultiplier()
	  {
		  return 1;
	  }
}
class Elves extends TribeObserver{
	Elves()
	{
		super(7);
	}
   @Override
	public void update(WeatherType currentWeather) {
	    LOGGER.info("Elves observer weather now", currentWeather);
	    if(currentWeather ==WeatherType.RAINY)
	    {
	    mood+=1;}
	    else if(currentWeather == WeatherType.WINDY)
	    {
	    mood+=2;
	    }
	    else {
	    {
	    mood+=1;}
		}
	  }
   public int reactionStrength()
	{
		return baseStrength()* strategyMultiplier();
	}
	protected int baseStrength()
	{
		return Math.max(1,mood);
	}
	protected int strategyMultiplier()
	{
		return 1;
	}
}
