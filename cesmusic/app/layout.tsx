import "./globals.css";

import { Kodchasan } from 'next/font/google'

const kodchasan = Kodchasan ({
  subsets: ['latin'],
  variable: '--font-kodchasan',
  weight: '400'
})


export default function RootLayout({children}: Readonly<{children: React.ReactNode}>) {
  return (
    <html lang="en">
      <body className={kodchasan.className}>
        {children}
      </body>
    </html>
  );
}
