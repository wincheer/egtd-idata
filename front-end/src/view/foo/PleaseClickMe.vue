<template>
  <div class="pleeease-click-me" @click="emitGlobalClickEvent()"></div>
</template>

<script>
// Import the EventBus we just created.
import { EventBus } from './event-bus.js';

export default {
  data() {
    return {
      clickCount: 0
    }
  },

  methods: {
    emitGlobalClickEvent() {
      this.clickCount++;
      // Send the event on a channel (i-got-clicked) with a payload (the click count.)
      EventBus.$emit('i-got-clicked', this.clickCount);
    }
  }
}
</script>


// // Import the EventBus.
// import { EventBus } from './event-bus.js';

// // Listen for the i-got-clicked event and its payload.
// EventBus.$on('i-got-clicked', clickCount => {
//   console.log(`Oh, that's nice. It's gotten ${clickCount} clicks! :)`)
// });

// If you’d only like to listen for the first emission of an event, you can use 
// EventBus.$once(channel: string, callback(payload1,…)).

// Removing Event Listeners:

// // Import the EventBus we just created.
// import { EventBus } from './event-bus.js';

// // The event handler function.
// const clickHandler = function(clickCount) {
//   console.log(`Oh, that's nice. It's gotten ${clickCount} clicks! :)`)
// }

// // Listen to the event.
// EventBus.$on('i-got-clicked', clickHandler);

// // Stop listening.
// EventBus.$off('i-got-clicked', clickHandler);