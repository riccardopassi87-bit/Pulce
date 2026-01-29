<script setup>
import { ref, onMounted } from 'vue';

const actualDate = ref('')

const expiring = ref ({
    urgent: [],
    warning: [],
    upcoming: []
})

const fetchAlerts = async () => {
    try {
        const res = await fetch('http://localhost:8080/api/item/expiring');
        if (res.ok){
            expiring.value = await res.json();
        }
    } catch (error) {
        console.error("Failed to fetch alerts: ", error)
    }
}

onMounted(() => {
    const today = new Date()
    actualDate.value = today.toLocaleDateString('de-AT', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    })

    fetchAlerts();
})

</script>

<template>
    <div  class="fsf" id="all-notifications">
        
        <div v-if="expiring.urgent.length == 0 && expiring.warning.length == 0 && expiring.upcoming.length == 0" class="fsf" id="date-placeholder">
            <p>Today is: <span>{{ actualDate }}</span></p>
        </div>

        <div v-else class="expiration-parent fsf">
            <div id="actual-date">
                <p class="left">Product Name</p>
                <p>Amount Left</p>
                <p class="right">Today: {{ actualDate }}</p>
            </div>
        
            <div class="fsf" id="expiration-content">
                <div v-if="expiring.urgent.length > 0" class="expiration" id="urgent">
                    <h4>URGENT !- one week left -!</h4>
                    <ul class="fsf">
                        <li v-for="i in expiring.urgent" :key="i.id" class="list-info">
                            <p class="left">{{ i.name }}</p>
                            <p>{{ i.amount }}</p>
                            <p class="right">{{ i.expirationDate }}</p>
                        </li>
                    </ul>
                </div>
                <div v-if="expiring.warning.length > 0" class="expiration" id="warning">
                    <h4>Warning - 7 to 20 days left!</h4>
                    <ul class="fsf">
                        <li v-for="i in expiring.warning" :key="i.id" class="list-info">
                            <p class="left">{{ i.name }}</p>
                            <p>{{ i.amount }}</p>
                            <p class="right">{{ i.expirationDate }}</p>
                        </li>
                    </ul>
                </div>
                <div v-if="expiring.upcoming.length > 0" class="expiration" id="upcoming">
                    <h4>Upcoming - 20 to 30 days left!</h4>
                    <ul class="fsf">
                        <li v-for="i in expiring.upcoming" :key="i.id" class="list-info">
                            <p class="left">{{ i.name }}</p>
                            <p>{{ i.amount }}</p>
                            <p class="right">{{ i.expirationDate }}</p>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
#all-notifications{
    background-color: #333;
    border-radius: 5px;
    padding: 0.5%;
}
#date-placeholder{
    font-size: 3rem;
    justify-content: center;
    align-items: center;
}
.expiration-parent{
    flex-direction: column;
}
.expiration{
    flex: 1;
}
#actual-date{
    height:10%;
    display: flex;
    justify-content: space-between;
}
#actual-date p{
    font-size: 1.5rem;
}
#expiration-content{
    flex-direction: column;
    height: 90%;
}
.list-info{
    width: 100%;
    height: 2rem;
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid #777;
}
p{
    flex: 1;
    text-align: center;
    font-size: 1.2rem;
    color: white;
}
.left{
    text-align: left;
}
.right{
    text-align: right;
}
#actual-date{
    flex:1;
    flex-direction: row;
    font-size: 1.5rem;
    align-items: center;
    margin-bottom: 1%;
    padding: 1%;
    background-color: #222;
}
#urgent{
    border: 1px solid red;
}
#urgent h4{
    background-color: red;
}
#warning{
    border: 1px solid orange;
}
#warning h4{
    background-color: orange;
}
#upcoming{
    border: 1px solid rgb(255, 247, 0);
}
#upcoming h4{
    background-color: rgb(255, 247, 0);
}
h4{
    width: 100%;
    padding-left: 1%;
    color: #222;
}
ul{
    flex-direction: column;
}
span{
    color: rgb(187, 150, 80);
}
</style>