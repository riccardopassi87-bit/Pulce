// DROP DOWN menu contents
// must be changed manually by adding or removing elements

// type of ingredients
export const INGREDIENT_TYPE = ['Veggie', 'Cheese', 'Meat', 'Base', 'Others']
// type of allergenes
// the map defines key: CODE and value: LABEL
// the dropdown menu shows both key and value
// the code is necessary to be shown in the menu PRINTOUT
// the pizza will show the allergene code of the ingredients bound to it
export const ALLERGENE_MAP = [{code: 'A', label: 'A - Glutenhaltig'},{code: 'B', label:'B - Krebstiere'},{code:'C', label:'C - Eier'},
                        {code:'D', label:'D - Fish'},{code:'E', label:'E - Erdnüsse'},{code:'F', label:'F - Sojabohnen'},
                        {code:'G', label:'G - Milch/Laktose'},{code:'H', label:'H - Schalenfrüchte'},{code:'L', label:'L - Sellerie'},
                        {code:'M', label:'M - Senf'},{code: 'N', label:'N - Sesamsamen'},{code:'O', label:'O - Schwefeldioxid/Sulfite'},
                        {code:'P', label:'P - Lupinen'},{code:'R', label:'R - Weichtiere'},{code:'N/A', label:'No Allergene'}]
// allergene codes is what is then used for the printout
export const ALLERGENE_CODES = ALLERGENE_MAP.map(a => a.code);
// type of pizzas
export const PIZZA_TYPE = ['Normal', 'Vegetarian', 'Vegan']
// type of products
export const PRODUCT_TYPE = ['Alpe', 'Alpenzu', 'Bisquits', 'Snacks', 'Drinks', 'Pesto', 'Pasta', 'Antipasti', 'Olives', 'Wine', 'Others' ]