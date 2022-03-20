<script setup>
import Block from '@/components/blocksview/Block.vue'
const props = defineProps(['blocks'])
</script>

<template>
    <div class="d-flex blockchain mt-4">
        <Block
        class="rounded mx-auto d-flex"
        v-for="block in props.blocks"
        :key="block.index"
        :msg="block"
        />
  </div>
</template>