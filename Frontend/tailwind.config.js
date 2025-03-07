/** @type {import('tailwindcss').Config} */
import daisyui from "daisyui";
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      boxShadow: {
        'inner-md': 'inset 0 0.2rem 0.2rem rgba(0, 0, 0, 0.5)',
      },
    },
  },
  plugins: [daisyui],
  daisyui: {
    base: false,
    styled: true,
  },
}

