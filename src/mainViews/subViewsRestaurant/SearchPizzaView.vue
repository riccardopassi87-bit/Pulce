<script setup>
  import SearchTemplate from '@/commonViews/SearchTemplate.vue';
  import SearchPrompt from '@/commonViews/SearchPrompt.vue';
  import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
</script>

<template>
  <div class="parent-view fsf">
    <div id="search-pizza" class="fsf">
      <SearchTemplate>
          <template #left-search>
              <SearchPrompt>
                  <template #filter>
                      <p>Normal</p>
                      <p>Vegetarian</p>
                      <p>Vegan</p>
                  </template>
              </SearchPrompt>
          </template>
      </SearchTemplate>
      </div>
      <div class="footer-buttons">
        <ButtonsFooter
        :show-save="true"
        :show-modify="true"
        :show-remove="true"/>
      </div>
    </div>
</template>

<style scoped>
  #search-pizza{
    flex:9;
  }
</style>