export const INGREDIENT_TYPE = ['Veggie', 'Cheese', 'Meat', 'Base', 'Others']
export const ALLERGENE_MAP = [{code: 'A', label: 'A - Glutenhaltig'},{code: 'B', label:'B - Krebstiere'},{code:'C', label:'C - Eier'},
                        {code:'D', label:'D - Fish'},{code:'E', label:'E - Erdnüsse'},{code:'F', label:'F - Sojabohnen'},
                        {code:'G', label:'G - Milch/Laktose'},{code:'H', label:'H - Schalenfrüchte'},{code:'L', label:'L - Sellerie'},
                        {code:'M', label:'M - Senf'},{code: 'N', label:'N - Sesamsamen'},{code:'O', label:'O - Schwefeldioxid/Sulfite'},
                        {code:'P', label:'P - Lupinen'},{code:'R', label:'R - Weichtiere'},{code:'N/A', label:'No Allergene'}]
export const ALLERGENE_CODES = ALLERGENE_MAP.map(a => a.code);
export const PIZZA_TYPE = ['Normal', 'Vegetarian', 'Vegan']
export const PRODUCT_TYPE = ['Alpe', 'Alpenzu', 'Bisquits', 'Snacks', 'Drinks', 'Pesto', 'Pasta', 'Antipasti', 'Olives', 'Wine', 'Others' ]