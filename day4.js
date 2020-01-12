const Guard = require('./guard4.js')
// Advent of Code 2018
// Day 4.1
const date = require('date-and-time')
const fs = require('fs')
const pin = fs.readFileSync('puzzleInput.txt', 'utf-8')
const rows = pin.split('\n')

main(rows)

function main (rows) {
  // callback function ensures correct order of executions 
  // & puts rows in order
  let timeArr = []
  parseTimes(rows, function (retVal) {
    timeArr = retVal
  } )
  parseShifts(timeArr, function (retVal) {
    // returns map containing key=id, value=guard
    let maxSleep = 0
    let maxSleepId = null
    retVal.forEach(element => {
      if (element.totalSleep > maxSleep) {
        maxSleep = element.totalSleep
        maxSleepId = element.id
      }
    });
    console.log("Guard " + retVal.get(maxSleepId).id + " sleeps most at " + retVal.get(maxSleepId).mostAsleepIndex() + " and therefore final answer is: " + (retVal.get(maxSleepId).mostAsleepIndex()*retVal.get(maxSleepId).id)) 
    let mostSleep = 0
    let mostSleepId = null
    retVal.forEach(element => {
      if (element.mostAsleep() > mostSleep) {
        mostSleep = element.mostAsleep()
        mostSleepId = element.id
      }
    });
    console.log("Guard " + mostSleepId + " is asleep most at some minute and therefore " + (retVal.get(mostSleepId).mostAsleepIndex()*retVal.get(mostSleepId).id))
  })  
}

function parseShifts (rows, callback) {
  // parse guard shifts
  let guards = new Map() // guard[id, guardObject]
  let guard
  for (let i=0; i<rows.length; i++) {
    const regex = /(?:#)([0-9]+)/
    const match = regex.exec(rows[i].sub)
    if (match) {
      if (guards.has(match[1])) {
        guard = guards.get(match[1])
      } else {
        guard = new Guard(match[1])
        guards.set(guard.id, guard)
      }
    } else {
      // add shift
      // next two lines: falls asleep & wakes up
      guard.sleeps(rows[i].time, rows[i+1].time)
      i++
    }
  }
  callback(guards)
}

function parseTimes (rows, callback) {
  // parse times from rows (and organize them in order)
  let timeArray = []
  const regex = /[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]/
  rows.forEach(row => {
    let datetime = regex.exec(row)
    datetime = datetime[0].concat(':00')
    datetime = datetime.replace(/-/g, "/")
    let dd = new Date(datetime)
    timeArray.push({time: dd, sub: row})
  });
  // organize times
  timeArray = timeArray.sort(function(a,b) {
    return a.time - b.time
  });
  callback(timeArray)
}
