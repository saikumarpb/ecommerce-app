import React, { useEffect, useState } from "react";
import { Button, Card, Placeholder } from "react-bootstrap";
import { getProducts } from "../api/ProductService";
import { Product } from "../types";

function Products() {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    getProducts()
      .then(({ data }) => {
        setProducts(data);
      })
      .catch((e) => {
        alert("Something went wrong, please try after some time.");
      });
  }, []);

  const handleAddToCart = () => {
    alert("Feature not implemented.");
  };

  const handleCardClick = () => {
    alert("Feature not implemented.");
  };

  const renderPlaceHolderCards = (numberOfCards: number) => {
    return [...Array(numberOfCards)].map((_, i) => (
      <div className="p-2" key={i}>
        <Card
          style={{ width: "18rem", cursor: "pointer" }}
          onClick={handleCardClick}
        >
          <Card.Body>
            <Placeholder as={Card.Title} animation="wave">
              <Placeholder xs={6} />
            </Placeholder>
            <Placeholder as={Card.Text} animation="wave">
              <Placeholder xs={7} /> <Placeholder xs={4} />{" "}
              <Placeholder xs={4} /> <Placeholder xs={6} />{" "}
              <Placeholder xs={8} />
            </Placeholder>
            <Placeholder.Button variant="primary" xs={6} />
          </Card.Body>
        </Card>
      </div>
    ));
  };

  const renderProducts = () => {
    return products.map((product) => (
      <div className="p-2" key={product.id}>
        <Card style={{ width: "18rem" }}>
          <Card.Img variant="top" src={product.image} />
          <Card.Body>
            <Card.Title>{product.name}</Card.Title>
            <div className="d-flex align-items-center justify-content-between">
              <Card.Text className="mb-0">₹ {product.price}</Card.Text>
              <Button variant="outline-primary" onClick={handleAddToCart}>
                Add to cart
              </Button>
            </div>
          </Card.Body>
        </Card>
      </div>
    ));
  };

  return (
    <div className="p-4 d-flex flex-wrap justify-content-center">
      {/* TODO : Update logic with is loading */}
      {products.length > 0 ? renderProducts() : renderPlaceHolderCards(10)}
    </div>
  );
}

export default Products;
