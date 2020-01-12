class Guard {
  constructor (id) {
    this._id = id
    this._sleepingMinutes = Array(60).fill(0)
    this._totalSleep = 0
  }

  mostAsleep () {
    return Math.max(...this._sleepingMinutes)
  }

  mostAsleepIndex () {
    return this._sleepingMinutes.indexOf(Math.max(...this._sleepingMinutes))
  }

  get totalSleep () {
    return this._totalSleep
  }

  set totalSleep (sleep) {
    this._totalSleep = sleep
  }

  get sleepingMinutes () {
    return this._sleepingMinutes
  }

  set id (id) {
    this._id = id
  }

  get id () {
    return this._id
  }

  sleeps (sleep, wakeup) {
    // from sleep to wakeup 
    this._totalSleep += (wakeup.getMinutes()-sleep.getMinutes())
    for (let i=sleep.getMinutes(); i<(wakeup.getMinutes()); i++) {
      this._sleepingMinutes[i]++
    }
  }
}

module.exports = Guard
