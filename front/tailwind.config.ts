import path from 'path'

const imageB = path.join('../my-app/app/assets/electronic_composition.png')

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      backgroundImage: {
        "gradient-radial": "radial-gradient(var(--tw-gradient-stops))",
        "gradient-conic": "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
        "eletronic_composition": "url('./assets/eletronic_composition.jpg')",
        "audio_software": "url('./assets/audio_software_without_top_grading.jpg')",
        "musical_tecnology": "url('./assets/musical_tecnology_bg.jpg')",
      },
      fontFamily: {
        kodchasan: ['var(--font-kodchasan)'],
        lindel_hill: ['var(--font-lindenHill-regular)']
      }
    },
  },
  plugins: [],
};
