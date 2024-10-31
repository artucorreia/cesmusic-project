import localFont from "next/font/local";

export const lindenHill = localFont(
    {
      src: 
      [
        {
          path: '../public/font/LindenHill-Regular.ttf'
        }
      ],
      variable: "--font-lindenHill-regular"
    }
  )