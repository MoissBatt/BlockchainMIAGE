<script setup>
const props = defineProps(['msg'])
const block = props.msg
</script>

<template>
    <div class="card bg-block mb-3 border-0" style="max-width: 18rem;">
        <div class="card-header d-flex">
            <p class = "head-block">Block #{{block.index}}</p>
        </div>
        
        <div class="card-body">
            <h5 class="card-title">
                <i class="bi bi-person-circle"></i>
                {{block.creator}}</h5>
            <p class="card-text">
                <i class="bi bi-clock-fill"></i>
                {{(new Date(block.timestamp)).toLocaleString()}}</p>
            <p class="card-text d-flex text-break">
                <i class="bi bi-tag-fill pe-1"></i>
                {{block.hash}}</p>
            <p class="card-text">
                <i class="bi bi-key-fill"></i>
                {{block.nonce}}</p>
        </div>
    </div>
</template>

<style>
.head-block{
    margin:auto auto;
    font-weight: bold;
}

.card-header{
    background-color: #BFC3E7 !important;
}

.bg-block{
    background-color: #d9dbf1 !important;
    color : #183A41
}


</style>
