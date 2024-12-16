import React, { useRef, useEffect } from "react";
import { Canvas, useFrame } from "@react-three/fiber";
import { OrbitControls, useGLTF } from "@react-three/drei";
import * as THREE from "three";

// 3D Model Component
const BackThreeD = () => {
  const { scene } = useGLTF("/scene.gltf"); // Load your syringe model
  const modelRef = useRef();

  // Ref to store mouse position
  const mousePosition = useRef({ x: 0, y: 0 });

  // Track mouse movements globally
  useEffect(() => {
    const handleMouseMove = (event) => {
      const { clientX, clientY } = event;
      const x = (clientX / window.innerWidth) * 2 - 1; // Normalized mouse X
      const y = -(clientY / window.innerHeight) * 2 + 1; // Normalized mouse Y
      mousePosition.current = { x, y };
    };
    document.addEventListener("mousemove", handleMouseMove);

    return () => {
      document.removeEventListener("mousemove", handleMouseMove);
    };
  }, []);

  // Update model position
  useFrame(() => {
    if (modelRef.current) {
      const x = THREE.MathUtils.lerp(-5, 5, mousePosition.current.x);
      const y = THREE.MathUtils.lerp(-3, 3, mousePosition.current.y);
      modelRef.current.position.set(x, y, -10); // Move model in background
    }
  });

  return (
    <primitive
      ref={modelRef}
      object={scene}
      scale={1.5}
      position={[0, 0, -10]} // Place the model in the background
    />
  );
};

// Main Component
const App = () => {
  return (
    <div style={{ width: '100vw', height: '100vh' }}>
        <Canvas>
          <ambientLight intensity={0.5} />
          <directionalLight position={[0, 5, 5]} intensity={1} />
          <BackThreeD />
          <OrbitControls/>
        </Canvas>
    </div>
  );
};

export default App;
