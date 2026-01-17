export async function createIngredient(API_BASE,data) {
    const response = await fetch (API_BASE, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })

    if (!response.ok) {
        throw new Error(error || `Server error: ${response.status}`)
    }

    return true
}