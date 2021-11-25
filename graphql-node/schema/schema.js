const graphql = require('graphql');
const _ = require('lodash')

const { GraphQLObjectType, GraphQLString, GraphQLSchema,GraphQLList } = graphql;

const BookType = new GraphQLObjectType({
   name: 'Book',
   fields: () => ({
       id: {type: GraphQLString},
       title: {type: GraphQLString},
       genre: {type: GraphQLString},
       author: {
           type: AuthorType,
           resolve(parent, args){
               return _.find(authors, {id: parent.authorId})
           }
       }
    })
});

const AuthorType = new GraphQLObjectType({
    name: 'Author',
    fields: () => ({
        id: {type: GraphQLString},
        name: {type: GraphQLString},
        books: {
            type: new GraphQLList(BookType),
            resolve(parent, args) {
                return _.filter(books, {authorId: parent.id})
            },
        }
    })
});

const RootQuery = new GraphQLObjectType({
    name: 'RootQueryType',
    fields: {
        book: {
            type: BookType,
            args: {id: {type: GraphQLString}},
            resolve(parent, args){
                //code to get data from db / other source
                return _.find(books, {id: args.id})
            }
        },
        author: {
            type: AuthorType,
            args: {id: {type: GraphQLString}},
            resolve(parent, args){
                return _.find(authors, {id: args.id})
            }
        },
        books: {
            type: new GraphQLList(BookType),
            args: {},
            resolve(parent, args){
                return books
            }
        }
    }
})

//dummy data
let books = [
    {id: '1',title:'The Silmarillion', genre: 'Fantasy', authorId: '1'},
    {id: '2',title:'The Lord of the Rings', genre: 'Fantasy', authorId: '1'},
    {id: '3',title:'Thus Spoke Zarathustra', genre: 'Philosophy', authorId: '2'},
]

let authors = [
    {id: '1',name:'J. R. R. Tolkien'},
    {id: '2',name:'Friedrich Nietzsche'},
]

module.exports = new GraphQLSchema({
    query: RootQuery
})