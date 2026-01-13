<script setup>
  import SearchTemplate from '@/commonViews/SearchTemplate.vue';
  import SearchPrompt from '@/commonViews/SearchPrompt.vue';
  import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';

  const TYPE = ['Normal', 'Vegetarian', 'Vegan']
</script>

<template>
  <div class="parent-view fsf">
    <div id="search-pizza" class="fsf">
      <SearchTemplate>
          <template #left-search>
              <SearchPrompt>
                  <template #filter>
                      <select>
                        <option disabled.value=""></option>
                        <option v-for="t in TYPE" :key="t" :value="t">
                          {{ t }}
                        </option>
                      </select>
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
  select{
    width: 70%;
    background-color: #222;
  }
</style>