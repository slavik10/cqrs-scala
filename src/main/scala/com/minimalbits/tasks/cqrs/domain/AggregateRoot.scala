package com.minimalbits.tasks.cqrs.domain

import collection.mutable.ArrayBuffer
import com.minimalbits.tasks.cqrs.event.DomainEvent


/**
 * Created with IntelliJ IDEA.
 * User: a239597
 * Date: 5/1/12
 * Time: 12:55 PM
 * To change this template use File | Settings | File Templates.
 */

abstract class AggregateRoot(val id:String) {
  val changes = new ArrayBuffer[DomainEvent]()
  var version = 0;

  def uncommittedChanges = changes

  def markChangesCommitted() {
    changes.clear()
  }

  def applyChange(event:DomainEvent) {
    applyChangeInternal(event, true)
  }

  def loadFromHistory(history:List[DomainEvent]) {
    for (event <- history) {
      applyChangeInternal(event, false)
    }
  }

  /**
   * NOTE: to be overridden by derived classes
   * @param event a concrete implementation of the DomainEvent class
   * @param isNew a flag indicating whether the event should be dispatched to the query side
   */
  def applyChangeInternal(event:DomainEvent, isNew:Boolean)
}